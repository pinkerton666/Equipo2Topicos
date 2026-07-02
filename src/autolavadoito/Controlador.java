package AutoLavadoITO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 * Clase Controlador: Representa el núcleo de la lógica de negocio.
 * Se encarga de manipular los modelos, aplicar reglas de negocio y validar formatos.
 */
public class Controlador {
    // --- Atributos de Almacenamiento Interno ---
    private final ArrayList<Cliente> listaClientes;
    private final ArrayList<Servicio> catalogoServicios;
    private final ArrayList<Orden> listaOrdenes;
    private final ArrayList<Ticket> listaTickets;
    
    /**
     * Constructor: Inicializa las listas y precarga el catálogo de servicios básico.
     */
    public Controlador() {
        this.listaClientes = new ArrayList<>();
        this.catalogoServicios = new ArrayList<>();
        this.listaOrdenes = new ArrayList<>();
        this.listaTickets = new ArrayList<>();
        
        // Precarga de servicios disponibles en el negocio
        catalogoServicios.add(new Servicio("S1", "Lavado Básico", 50.0, 30, "Lavado exterior"));
        catalogoServicios.add(new Servicio("S2", "Lavado Completo", 100.0, 60, "Interior y exterior"));
        catalogoServicios.add(new Servicio("S3", "Encerado", 80.0, 45, "Encerado manual"));
    }
    
    /**
     * Registra un nuevo cliente aplicando reglas de validación de formato.
     * @return ID del nuevo cliente si es exitoso, o un código negativo si falla la validación.
     */
    public int crearCliente(String nombre, String apellido, String telefono) {
        // Validación: Solo letras y espacios para nombre y apellido
        if(!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || !apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return -2; // Código de error: Formato de nombre/apellido inválido
        }
        // Validación: Teléfono estrictamente de 10 dígitos numéricos
        if(!telefono.matches("^\\d{10}$")) {
            return -3; // Código de error: Formato de teléfono inválido
        }
        
        // Autoincremento del ID basado en el tamaño de la lista
        int nuevoId = listaClientes.size() + 1; 
        listaClientes.add(new Cliente(nuevoId, nombre, apellido, telefono));
        return nuevoId;
    }

    /**
     * Busca un cliente en el arreglo interno mediante su identificador único.
     */
    public Cliente buscarClientePorId(int idCliente) {
        for(Cliente cliente : listaClientes){
            if(cliente.getIdCliente() == idCliente){
                return cliente;
            }
        }
        return null; // Retorna null si no se encuentra
    }

    /**
     * Vincula un nuevo vehículo a un cliente existente.
     * @return ID del nuevo auto si es exitoso, u otro código negativo si hay fallas.
     */
    public int registrarVehiculo(int idCliente, String marca, String modelo, String color, String tipo, String obs) {
        Cliente propietario = buscarClientePorId(idCliente);
        if (propietario == null) {
            return -1; // Código de error: Cliente no encontrado
        }
        
        // Validación: Evitar caracteres especiales en datos del coche
        String regexAlfaNumerico = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$";
        if(!marca.matches(regexAlfaNumerico) || !modelo.matches(regexAlfaNumerico) || !color.matches(regexAlfaNumerico)) {
            return -4; // Código de error: Formato automotriz inválido
        }
        
        // El ID del auto es local e incremental según los autos que ya posea el cliente
        int nuevoIdAuto = propietario.getListaVehiculos().size() + 1; 
        Vehiculo nuevoVehiculo = new Vehiculo(nuevoIdAuto, marca, modelo, color, tipo, obs, propietario);
        propietario.agregarVehiculo(nuevoVehiculo);
        return nuevoIdAuto;
    }

    /**
     * Localiza un vehículo específico cruzando el ID del propietario y el del coche.
     */
    public Vehiculo buscarVehiculoPorIds(int idCliente, int idAuto) {
        Cliente c = buscarClientePorId(idCliente);
        if (c == null) return null;
        for(Vehiculo vehiculo : c.getListaVehiculos()){
            if(vehiculo.getIdAuto() == idAuto){
                return vehiculo;
            }
        }
        return null;
    }

    /**
     * Crea una orden de servicio en el negocio evaluando restricciones de agenda si es reservación.
     * @return Folio de la orden generada, o código negativo si las fechas son inválidas.
     */
    public int crearOrden(Vehiculo vehiculo, boolean reservacion, Date fechaReserva, String obs) {
        // Validaciones del flujo de Reservas
        if(reservacion) {
            if(fechaReserva == null) {
                return -5; // Código de error: Fecha no seleccionada
            }
            
            // Reestablecer horas/minutos para comparar únicamente las fechas actuales por día
            Calendar calHoy = Calendar.getInstance();
            calHoy.set(Calendar.HOUR_OF_DAY, 0); calHoy.set(Calendar.MINUTE, 0);
            calHoy.set(Calendar.SECOND, 0); calHoy.set(Calendar.MILLISECOND, 0);
            Date hoy = calHoy.getTime();

            if(fechaReserva.before(hoy)) {
                return -6; // Código de error: Fecha en el pasado
            }
        }
        
        int nuevoFolio = listaOrdenes.size() + 1;
        Orden nuevaOrden = new Orden(nuevoFolio, vehiculo.getPropietario(), vehiculo, reservacion, fechaReserva, obs);
        listaOrdenes.add(nuevaOrden);
        return nuevoFolio;
    }

    /**
     * Devuelve la lista estática de servicios que ofrece el autolavado.
     */
    public ArrayList<Servicio> getCatalogoServicios() { return catalogoServicios; }

    /**
     * Recupera una orden de servicio mediante su folio único de rastreo.
     */
    public Orden buscarOrdenPorFolio(int folio) {
        for(Orden orden : listaOrdenes){
            if(orden.getIdOrden() == folio){
                return orden;
            }
        }
        return null;
    }

    /**
     * Finaliza la estancia del coche en el establecimiento, calcula montos y emite un Ticket de pago.
     */
    public Ticket procesarSalidaYTicket(int folio, double pago, String tipoPago) {
        Orden orden = buscarOrdenPorFolio(folio);
        if (orden == null) return null; 
        
        orden.registrarSalida(); // Sella la fecha/hora de salida actual
        Ticket nuevoTicket = new Ticket(orden, pago, tipoPago);
        listaTickets.add(nuevoTicket);
        return nuevoTicket;
    }

    /**
     * Genera la estructura de datos tabulada para alimentar el JTable de la interfaz.
     */
    public DefaultTableModel generarModeloTablaOrdenes() {
        String[] columnas = {"Folio", "Cliente", "Auto", "Reserva", "Total", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Celdas no editables
        };
        
        // Poblado de filas recorriendo la lista dinámica del negocio
        for (Orden o : listaOrdenes) {
            Object[] fila = { 
                o.getIdOrden(), 
                o.getCliente().getNombre(), 
                o.getVehiculo().getMarca(), 
                o.getFechaReserva() != null ? o.getFechaReserva().toString() : "No",
                "$" + o.getCostoFinal(), 
                o.getFechaSalida() == null ? "En Proceso" : "Pagado" 
            };
            modelo.addRow(fila);
        }
        return modelo;
    }
}