package AutoLavadoITO;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class Controlador {
    private final ArrayList<Cliente> listaClientes;
    private final ArrayList<Servicio> catalogoServicios;
    private final ArrayList<Orden> listaOrdenes;
    private final ArrayList<Ticket> listaTickets;
    
    public Controlador() {
        this.listaClientes = new ArrayList<>();
        this.catalogoServicios = new ArrayList<>();
        this.listaOrdenes = new ArrayList<>();
        this.listaTickets = new ArrayList<>();
        
        catalogoServicios.add(new Servicio("S1", "Lavado Básico", 50.0, 30, "Lavado exterior"));
        catalogoServicios.add(new Servicio("S2", "Lavado Completo", 100.0, 60, "Interior y exterior"));
        catalogoServicios.add(new Servicio("S3", "Encerado", 80.0, 45, "Encerado manual"));
    }
    
    public int crearCliente(String nombre, String apellido, String telefono) 
    {
        if(nombre.trim().isEmpty()) return -1;
        int nuevoId = listaClientes.size()+1; 
        listaClientes.add(new Cliente(nuevoId, nombre, apellido, telefono));
        return nuevoId;
    }

    public Cliente buscarClientePorId(int idCliente) {
        for(Cliente cliente:listaClientes){
            if(cliente.getIdCliente()==idCliente){
                return cliente;
            }
        }
        return null;
    }

    public int registrarVehiculo(int idCliente, String marca, String modelo, String color, String tipo, String obs) 
    {
        Cliente propietario = buscarClientePorId(idCliente);
        if (propietario == null) return -1;
        
        int nuevoIdAuto = propietario.getListaVehiculos().size()+1; 
        Vehiculo nuevoVehiculo = new Vehiculo(nuevoIdAuto, marca, modelo, color, tipo, obs, propietario);
        propietario.agregarVehiculo(nuevoVehiculo);
        return nuevoIdAuto;
    }

    public Vehiculo buscarVehiculoPorIds(int idCliente, int idAuto) {
        Cliente c = buscarClientePorId(idCliente);
        //if (c != null && idAuto >= 0 && idAuto < c.getListaVehiculos().size()) return c.getListaVehiculos().get(idAuto);
        
        for(Vehiculo vehiculo:c.getListaVehiculos()){
            if(vehiculo.getIdAuto()==idAuto){
                return vehiculo;
            }
        }
        return null;
    }

    // AHORA EL FOLIO SE GENERA AUTOMÁTICAMENTE Y SE RETORNA
    public int crearOrden(Vehiculo vehiculo, boolean reservacion, Date fechaReserva, String obs) {
        if (vehiculo == null) return -1;
        int nuevoFolio = listaOrdenes.size()+1; // Folio automático
        Orden nuevaOrden = new Orden(nuevoFolio, vehiculo.getPropietario(), vehiculo, reservacion, fechaReserva, obs);
        listaOrdenes.add(nuevaOrden);
        return nuevoFolio;
    }

    public ArrayList<Servicio> getCatalogoServicios() { return catalogoServicios; }

    // NUEVO: Busca una orden directamente por su FOLIO
    public Orden buscarOrdenPorFolio(int folio) {
        /*if (folio >= 0 && folio < listaOrdenes.size()) {
            Orden o = listaOrdenes.get(folio);
            if (o.getFechaSalida() == null) { // Solo regresa si NO ha sido cobrada (está activa)
                return o;
            }
        }*/
        for(Orden orden:listaOrdenes){
            if(orden.getIdOrden()==folio){
                return orden;
            }
        }
        return null;
    }

    // AHORA EL COBRO SE HACE POR FOLIO
    public Ticket procesarSalidaYTicket(int folio, double pago, String tipoPago) {
        Orden orden = buscarOrdenPorFolio(folio);
        if (orden == null) return null; // Si no existe o ya se cobró
        orden.registrarSalida();
        Ticket nuevoTicket = new Ticket(orden, pago, tipoPago);
        listaTickets.add(nuevoTicket);
        return nuevoTicket;
    }

    public DefaultTableModel generarModeloTablaOrdenes() {
        String[] columnas = {"Folio", "Cliente", "Auto", "Reserva", "Total", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
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