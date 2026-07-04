package AutoLavadoITO;

import componentes.CampoTextoValidado;
import componentes.SelectorTipoVehiculo;
import componentes.BannerBienvenida;
import componentes.BotonExportarTicketPDF;
import componentes.TextFieldBusquedaCliente;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.ArrayList;

/**
 * Clase Vista (framePrincipal) optimizada.
 * Centraliza la interfaz gráfica utilizando un contenedor de pestañas transparentes,
 * validación activa de tipos de datos, selectores dinámicos basados en imágenes 
 * y un módulo de exportación de comprobantes en formato HTML/PDF.
 */
public class framePrincipal extends javax.swing.JFrame {
    
    // --- Atributos de Configuración y UI ---
    private Controlador controlador;
    private javax.swing.JTabbedPane jTabbedPane1;
    private FondoPanel fondo = new FondoPanel();
    private BannerBienvenida bannerBienvenida;
    
    // TAB 1: Registro completo inicial
    private javax.swing.JPanel panelRegistro;
    private javax.swing.JTextField txtNombreCli, txtApellidoCli, txtTelefonoCli;
    private javax.swing.JTextField txtMarca, txtModelo, txtColor, txtObservacionesV;
    private SelectorTipoVehiculo cmbTipoVehiculo;
    private javax.swing.JButton btnRegistrarCliYVeh;

    // TAB 2: Agregar auto a cliente existente
    private javax.swing.JPanel panelAgregarAuto;
    private javax.swing.JLabel lblCliEncontrado;
    private javax.swing.JTextField txtMarca2, txtModelo2, txtColor2, txtObservacionesV2;
    private SelectorTipoVehiculo cmbTipoVehiculo2;
    private javax.swing.JButton btnAgregarSoloAuto;
    private componentes.TextFieldBusquedaCliente txtIdClienteBuscar;
    
    // TAB 3: Gestión de Órdenes y Servicios adicionales
    private javax.swing.JPanel panelOrdenes;
    private componentes.TextFieldBusquedaCliente txtIdClienteOrd; // Modificado a componente de búsqueda
    private javax.swing.JLabel lblCliEncontradoOrd; // Agregado para confirmación visual en Tab 3
    private javax.swing.JTextField txtIdAutoOrd, txtFolioServicio;
    private javax.swing.JCheckBox chkReserva;
    private com.toedter.calendar.JDateChooser jdFechaReserva;
    private javax.swing.JComboBox<String> cmbServiciosDisponibles;
    private javax.swing.JButton btnCrearOrden, btnAgregarServicio;

    // TAB 4: Módulo de Facturación, Caja y visualización de Ticket
    private javax.swing.JPanel panelSalida;
    private javax.swing.JTextField txtFolioCobro, txtMontoPago;
    private javax.swing.JComboBox<String> cmbTipoPago;
    private javax.swing.JButton btnCobrar;
    private BotonExportarTicketPDF btnExportarTicket;
    private javax.swing.JTextArea txtAreaTicket;

    // TAB 5: Bitácora de control interno (Tabla global)
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tblGeneral;
    private javax.swing.JButton btnActualizarTabla;

    public framePrincipal(Controlador controlador) {
        this.controlador = controlador;
        this.setContentPane(fondo); 
        
        initComponents();
        cargarServiciosCombo();
        configureEventosPago();
        configurarEventosReserva(); 
        btnActualizarTablaActionPerformed(null); 
        
        // Conexión del buscador de la pestaña 2
        txtIdClienteBuscar.conectarControlador(controlador, controlador.getListaClientes(), new componentes.TextFieldBusquedaCliente.OyenteResultadoBusqueda() {
            @Override
            public void registrarResultados(ArrayList<Cliente> clientesEncontrados) {
                if (!clientesEncontrados.isEmpty()) {
                    Cliente primerCoincidencia = clientesEncontrados.get(0);
                    lblCliEncontrado.setText("ID: " + primerCoincidencia.getIdCliente() + " - " + primerCoincidencia.getNombre() + " " + primerCoincidencia.getApellido());
                } else {
                    lblCliEncontrado.setText("No hay coincidencias.");
                }
            }
        });

        // NUEVO: Conexión del buscador de la pestaña 3
        txtIdClienteOrd.conectarControlador(controlador, controlador.getListaClientes(), new componentes.TextFieldBusquedaCliente.OyenteResultadoBusqueda() {
            @Override
            public void registrarResultados(ArrayList<Cliente> clientesEncontrados) {
                if (!clientesEncontrados.isEmpty()) {
                    Cliente primerCoincidencia = clientesEncontrados.get(0);
                    lblCliEncontradoOrd.setText("ID: " + primerCoincidencia.getIdCliente() + " - " + primerCoincidencia.getNombre() + " " + primerCoincidencia.getApellido());
                } else {
                    lblCliEncontradoOrd.setText("No hay coincidencias.");
                }
            }
        });
    }

    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane1.setOpaque(false);
        
        bannerBienvenida = new BannerBienvenida();
        
        // --- CONSTRUCCIÓN TAB 1 ---
        panelRegistro = new javax.swing.JPanel(new java.awt.GridLayout(9, 2, 5, 5));
        panelRegistro.setOpaque(false);
        
        txtNombreCli = new CampoTextoValidado();
        ((CampoTextoValidado) txtNombreCli).setTipoValidacion("LETRAS");

        txtApellidoCli = new CampoTextoValidado();
        ((CampoTextoValidado) txtApellidoCli).setTipoValidacion("LETRAS");

        txtTelefonoCli = new CampoTextoValidado();
        ((CampoTextoValidado) txtTelefonoCli).setTipoValidacion("TELEFONO");

        txtMarca = new CampoTextoValidado();
        ((CampoTextoValidado) txtMarca).setTipoValidacion("MARCA");

        txtModelo = new CampoTextoValidado();
        ((CampoTextoValidado) txtModelo).setTipoValidacion("MODELO");

        txtColor = new CampoTextoValidado();
        ((CampoTextoValidado) txtColor).setTipoValidacion("COLOR");

        txtObservacionesV = new CampoTextoValidado();
        ((CampoTextoValidado) txtObservacionesV).setTipoValidacion("Observaciones");
        
        cmbTipoVehiculo = new SelectorTipoVehiculo();
        btnRegistrarCliYVeh = new javax.swing.JButton("Registrar Cliente y Auto");
        
        panelRegistro.add(new javax.swing.JLabel("Nombre:")); panelRegistro.add(txtNombreCli);
        panelRegistro.add(new javax.swing.JLabel("Apellido:")); panelRegistro.add(txtApellidoCli);
        panelRegistro.add(new javax.swing.JLabel("Teléfono:")); panelRegistro.add(txtTelefonoCli);
        panelRegistro.add(new javax.swing.JLabel("Marca Auto:")); panelRegistro.add(txtMarca);
        panelRegistro.add(new javax.swing.JLabel("Modelo:")); panelRegistro.add(txtModelo);
        panelRegistro.add(new javax.swing.JLabel("Color:")); panelRegistro.add(txtColor);
        panelRegistro.add(new javax.swing.JLabel("Tipo:")); panelRegistro.add(cmbTipoVehiculo);
        panelRegistro.add(new javax.swing.JLabel("Observaciones:")); panelRegistro.add(txtObservacionesV);
        panelRegistro.add(new javax.swing.JLabel("")); panelRegistro.add(btnRegistrarCliYVeh);
        jTabbedPane1.addTab("1. Cliente Nuevo", panelRegistro);

        // --- CONSTRUCCIÓN TAB 2 ---
        panelAgregarAuto = new javax.swing.JPanel(new java.awt.GridLayout(8, 2, 5, 5));
        panelAgregarAuto.setOpaque(false);
        
        txtIdClienteBuscar = new componentes.TextFieldBusquedaCliente();
        lblCliEncontrado = new javax.swing.JLabel("...");
        
        txtMarca2 = new javax.swing.JTextField(); 
        txtModelo2 = new javax.swing.JTextField();
        txtColor2 = new javax.swing.JTextField(); 
        txtObservacionesV2 = new javax.swing.JTextField();
        cmbTipoVehiculo2 = new SelectorTipoVehiculo();
        btnAgregarSoloAuto = new javax.swing.JButton("Agregar Auto");

        panelAgregarAuto.add(new javax.swing.JLabel("Nombre del Cliente:")); panelAgregarAuto.add(txtIdClienteBuscar);
        panelAgregarAuto.add(new javax.swing.JLabel("Cliente Seleccionado:")); panelAgregarAuto.add(lblCliEncontrado);
        panelAgregarAuto.add(new javax.swing.JLabel("Marca Auto:")); panelAgregarAuto.add(txtMarca2);
        panelAgregarAuto.add(new javax.swing.JLabel("Modelo:")); panelAgregarAuto.add(txtModelo2);
        panelAgregarAuto.add(new javax.swing.JLabel("Color:")); panelAgregarAuto.add(txtColor2);
        panelAgregarAuto.add(new javax.swing.JLabel("Tipo:")); panelAgregarAuto.add(cmbTipoVehiculo2);
        panelAgregarAuto.add(new javax.swing.JLabel("Observaciones:")); panelAgregarAuto.add(txtObservacionesV2);
        panelAgregarAuto.add(new javax.swing.JLabel("")); panelAgregarAuto.add(btnAgregarSoloAuto);
        jTabbedPane1.addTab("2. Auto Nuevo", panelAgregarAuto);

        // --- CONSTRUCCIÓN TAB 3 (Ajustado a 9 filas para albergar la confirmación del cliente) ---
        panelOrdenes = new javax.swing.JPanel(new java.awt.GridLayout(9, 2, 5, 5));
        panelOrdenes.setOpaque(false);
        
        txtIdClienteOrd = new componentes.TextFieldBusquedaCliente(); // Inicializado como componente dinámico
        lblCliEncontradoOrd = new javax.swing.JLabel("...");
        txtIdAutoOrd = new javax.swing.JTextField(); 
        txtFolioServicio = new javax.swing.JTextField();
        chkReserva = new javax.swing.JCheckBox("¿Es Reserva?");
        chkReserva.setOpaque(false);
        jdFechaReserva = new com.toedter.calendar.JDateChooser();
        jdFechaReserva.setEnabled(false); 
        btnCrearOrden = new javax.swing.JButton("1. Crear Orden (Genera Folio)");
        
        cmbServiciosDisponibles = new javax.swing.JComboBox<>();
        btnAgregarServicio = new javax.swing.JButton("2. Añadir Servicio al Folio");

        panelOrdenes.add(new javax.swing.JLabel("Nombre del Cliente:")); panelOrdenes.add(txtIdClienteOrd);
        panelOrdenes.add(new javax.swing.JLabel("Cliente Seleccionado:")); panelOrdenes.add(lblCliEncontradoOrd);
        panelOrdenes.add(new javax.swing.JLabel("ID Auto:")); panelOrdenes.add(txtIdAutoOrd);
        panelOrdenes.add(chkReserva); panelOrdenes.add(jdFechaReserva);
        panelOrdenes.add(new javax.swing.JLabel("")); panelOrdenes.add(btnCrearOrden);
        panelOrdenes.add(new javax.swing.JLabel("--- AGREGAR SERVICIOS ---")); panelOrdenes.add(new javax.swing.JLabel("-----------------------"));
        panelOrdenes.add(new javax.swing.JLabel("Ingrese Folio de Orden:")); panelOrdenes.add(txtFolioServicio);
        panelOrdenes.add(new javax.swing.JLabel("Seleccione Servicio:")); panelOrdenes.add(cmbServiciosDisponibles);
        panelOrdenes.add(new javax.swing.JLabel("")); panelOrdenes.add(btnAgregarServicio);
        jTabbedPane1.addTab("3. Generar Orden y Servicios", panelOrdenes);

        // --- CONSTRUCCIÓN TAB 4 ---
        panelSalida = new javax.swing.JPanel(new java.awt.BorderLayout());
        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.GridLayout(5, 2));
        topPanel.setOpaque(false);
        panelSalida.setOpaque(false);
        
        txtFolioCobro = new javax.swing.JTextField();
        txtMontoPago = new javax.swing.JTextField();
        cmbTipoPago = new javax.swing.JComboBox<>(new String[]{"Efectivo", "Tarjeta"});
        btnCobrar = new javax.swing.JButton("Cobrar y Ticket");
        btnExportarTicket = new BotonExportarTicketPDF();
        
        topPanel.add(new javax.swing.JLabel("Folio de Orden a Cobrar:")); topPanel.add(txtFolioCobro);
        topPanel.add(new javax.swing.JLabel("Monto Pagado:")); topPanel.add(txtMontoPago);
        topPanel.add(new javax.swing.JLabel("Tipo Pago:")); topPanel.add(cmbTipoPago);
        topPanel.add(new javax.swing.JLabel("")); topPanel.add(btnCobrar);
        topPanel.add(new javax.swing.JLabel("")); topPanel.add(btnExportarTicket);
        
        txtAreaTicket = new javax.swing.JTextArea(15, 30);
        panelSalida.add(topPanel, java.awt.BorderLayout.NORTH);
        panelSalida.add(new javax.swing.JScrollPane(txtAreaTicket), java.awt.BorderLayout.CENTER);
        jTabbedPane1.addTab("4. Cobro y Salida", panelSalida);

        // --- CONSTRUCCIÓN TAB 5 ---
        panelTabla = new javax.swing.JPanel(new java.awt.BorderLayout());
        panelTabla.setOpaque(false);
        tblGeneral = new javax.swing.JTable();
        btnActualizarTabla = new javax.swing.JButton("Actualizar Vistas");
        panelTabla.add(new javax.swing.JScrollPane(tblGeneral), java.awt.BorderLayout.CENTER);
        panelTabla.add(btnActualizarTabla, java.awt.BorderLayout.SOUTH);
        jTabbedPane1.addTab("5. Control Interno", panelTabla);

        // Asignación de Listeners
        btnRegistrarCliYVeh.addActionListener(this::btnRegistrarCliYVehActionPerformed);
        btnAgregarSoloAuto.addActionListener(this::btnAgregarSoloAutoActionPerformed);
        btnCrearOrden.addActionListener(this::btnCrearOrdenActionPerformed);
        btnAgregarServicio.addActionListener(this::btnAgregarServicioActionPerformed);
        btnCobrar.addActionListener(this::btnCobrarActionPerformed);
        btnExportarTicket.addActionListener(this::btnExportarTicketActionPerformed);
        btnActualizarTabla.addActionListener(this::btnActualizarTablaActionPerformed);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        
        // Acoplamiento jerárquico del Banner y los Tabs sobre el panel base con fondo
        this.getContentPane().add(bannerBienvenida, java.awt.BorderLayout.NORTH);
        this.getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        
        setSize(740, 640); 
        setLocationRelativeTo(null);
    }

    private void cargarServiciosCombo() {
        for(Servicio s : controlador.getCatalogoServicios()) {
            cmbServiciosDisponibles.addItem(s.getNombre() + " - $" + s.getPrecio());
        }
    }

    private void configureEventosPago() {
        cmbTipoPago.addActionListener(e -> {
            try {
                String folioStr = txtFolioCobro.getText().trim();
                if (folioStr.isEmpty()) return;

                int folio = Integer.parseInt(folioStr);
                Orden o = controlador.buscarOrdenPorFolio(folio);

                if (o != null) {
                    if (cmbTipoPago.getSelectedItem().toString().equals("Tarjeta")) {
                        txtMontoPago.setText(String.valueOf(o.getCostoFinal()));
                        txtMontoPago.setEditable(false);
                    } else {
                        txtMontoPago.setEditable(true);
                        txtMontoPago.setText("");
                    }
                }
            } catch (Exception ex) { }
        });
    }

    private void configurarEventosReserva() {
        chkReserva.addActionListener(e -> {
            if (chkReserva.isSelected()) {
                jdFechaReserva.setEnabled(true);
            } else {
                jdFechaReserva.setEnabled(false);
                jdFechaReserva.setDate(null);
            }
        });
    }

    private void btnRegistrarCliYVehActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = txtNombreCli.getText().trim();
        String apellido = txtApellidoCli.getText().trim();
        String tel = txtTelefonoCli.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String color = txtColor.getText().trim();

        if(nombre.isEmpty() || apellido.isEmpty() || tel.isEmpty() || marca.isEmpty() || modelo.isEmpty() || color.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rellene todas las casillas.", "Casillas vacías", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCli = controlador.crearCliente(nombre, apellido, tel);
        if (idCli == -2) {
            JOptionPane.showMessageDialog(this, "El nombre y apellido no pueden contener números ni signos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (idCli == -3) {
            JOptionPane.showMessageDialog(this, "El teléfono solo debe contener 10 dígitos numéricos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idAuto = controlador.registrarVehiculo(idCli, marca, modelo, color, cmbTipoVehiculo.getTipoSeleccionado(), txtObservacionesV.getText());
        if (idAuto == -4) {
            JOptionPane.showMessageDialog(this, "La marca, modelo y color del auto no deben contener signos especiales.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "¡Éxito!\nTu ID Cliente es: " + idCli + "\nEl ID de Auto es: " + idAuto);
        
        txtNombreCli.setText(""); txtApellidoCli.setText(""); txtTelefonoCli.setText(""); 
        txtMarca.setText(""); txtModelo.setText(""); txtColor.setText(""); txtObservacionesV.setText("");
    }

  private void btnAgregarSoloAutoActionPerformed(java.awt.event.ActionEvent evt) {
    // 1. Convertir el contenido del JLabel en String
    String textoCliente = lblCliEncontrado.getText().trim();
    
    if(textoCliente.equals("...") || textoCliente.equals("No hay coincidencias.")) {
        JOptionPane.showMessageDialog(this, "Por favor, busque y seleccione un cliente válido primero.", "Cliente no seleccionado", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String marca = txtMarca2.getText().trim();
    String modelo = txtModelo2.getText().trim();
    String color = txtColor2.getText().trim();
    String obs = txtObservacionesV2.getText().trim();

    if(marca.isEmpty() || modelo.isEmpty() || color.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Marca, Modelo y Color son obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // 2. Extraer el INT: Quitamos el prefijo "ID: ", separamos por espacios y tomamos la primera parte
        String idLimpio = textoCliente.replace("ID: ", "").split(" ")[0];
        int idCli = Integer.parseInt(idLimpio);
        
        // 3. Registrar el vehículo usando el ID extraído
        int idAuto = controlador.registrarVehiculo(idCli, marca, modelo, color, cmbTipoVehiculo2.getTipoSeleccionado(), obs);
        
        if(idAuto == -1) {
            JOptionPane.showMessageDialog(this, "No se encontró el cliente con ID: " + idCli, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (idAuto == -4) {
            JOptionPane.showMessageDialog(this, "La marca, modelo y color no deben contener signos especiales.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Auto agregado exitosamente.\nEl ID de este Auto es: " + idAuto + " para el Cliente ID: " + idCli);
            txtMarca2.setText(""); txtModelo2.setText(""); txtColor2.setText(""); txtObservacionesV2.setText("");
        }
    } catch(Exception e) { 
        JOptionPane.showMessageDialog(this, "Error al extraer el ID numérico del cliente.", "Error", JOptionPane.ERROR_MESSAGE); 
    }
}

  private void btnCrearOrdenActionPerformed(java.awt.event.ActionEvent evt) {
    // 1. Convertir el contenido del JLabel en String
    String textoClienteOrd = lblCliEncontradoOrd.getText().trim();
    String idAutoStr = txtIdAutoOrd.getText().trim();

    if(textoClienteOrd.equals("...") || textoClienteOrd.equals("No hay coincidencias.")) {
        JOptionPane.showMessageDialog(this, "Por favor, busque y seleccione un cliente en esta pestaña primero.", "Cliente omitido", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if(idAutoStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El ID de Auto es obligatorio.");
        return;
    }

    try {
        // 2. Extraer el INT: Quitamos el prefijo "ID: ", separamos por espacios y tomamos la primera parte
        String idLimpio = textoClienteOrd.replace("ID: ", "").split(" ")[0];
        int idCli = Integer.parseInt(idLimpio);

        // 3. Buscar el vehículo y crear la orden
        Vehiculo v = controlador.buscarVehiculoPorIds(idCli, Integer.parseInt(idAutoStr));
        if(v == null) {
            JOptionPane.showMessageDialog(this, "Vehículo o Cliente no asociado correctamente. Verifique el ID de Auto.");
            return;
        }

        Date fecha = chkReserva.isSelected() ? jdFechaReserva.getDate() : null;
        int folioGen = controlador.crearOrden(v, chkReserva.isSelected(), fecha, "");
        
        if (folioGen == -5) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fecha en el calendario.");
        } else if (folioGen == -6) {
            JOptionPane.showMessageDialog(this, "No se permiten fechas anteriores al día de hoy.", "Fecha Inválida", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "ORDEN CREADA EXITOSAMENTE.\n\n** TU FOLIO ES: " + folioGen + " **\n\nUsa este número para añadir servicios y cobrar.");
            txtFolioServicio.setText(String.valueOf(folioGen));
            txtFolioCobro.setText(String.valueOf(folioGen));
        }
    } catch(NumberFormatException e) { 
        JOptionPane.showMessageDialog(this, "El ID de Auto debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE); 
    } catch(Exception e) {
        JOptionPane.showMessageDialog(this, "Error al procesar el ID extraído para la orden.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void btnAgregarServicioActionPerformed(java.awt.event.ActionEvent evt) {
        String folioStr = txtFolioServicio.getText().trim();
        if(folioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un Folio de Orden.");
            return;
        }

        try {
            int folio = Integer.parseInt(folioStr);
            Orden o = controlador.buscarOrdenPorFolio(folio);
            
            if (o != null) {
                o.agregarServicio(controlador.getCatalogoServicios().get(cmbServiciosDisponibles.getSelectedIndex()));
                JOptionPane.showMessageDialog(this, "Servicio añadido a la Orden #" + folio + ".\nTotal a pagar acumulado: $" + o.getCostoFinal());
            } else { 
                JOptionPane.showMessageDialog(this, "No hay una orden activa con el Folio: " + folio); 
            }
        } catch(NumberFormatException e) { 
            JOptionPane.showMessageDialog(this, "Por favor ingrese un Folio numérico válido."); 
        }
    }

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {
        String folioStr = txtFolioCobro.getText().trim();
        String pagoStr = txtMontoPago.getText().trim();

        if(folioStr.isEmpty() || pagoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Folio y Monto son obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int folio = Integer.parseInt(folioStr);
            double pago = Double.parseDouble(pagoStr);
            String tipoPago = cmbTipoPago.getSelectedItem().toString();
            
            Orden o = controlador.buscarOrdenPorFolio(folio);
            if (o == null) {
                JOptionPane.showMessageDialog(this, "La orden específica no existe o ya fue pagada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tipoPago.equals("Efectivo") && pago < o.getCostoFinal()) {
                JOptionPane.showMessageDialog(this, "El monto ingresado es menor al total a pagar ($" + o.getCostoFinal() + ").", "Pago Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Ticket t = controlador.procesarSalidaYTicket(folio, pago, tipoPago);
            if (t != null) {
                txtAreaTicket.setText(t.toString());
                btnActualizarTablaActionPerformed(null); 
            } else { 
                JOptionPane.showMessageDialog(this, "Error al procesar la salida."); 
            }
        } catch (NumberFormatException e) { 
            JOptionPane.showMessageDialog(this, "Folio o monto con formato inválido.", "Error", JOptionPane.ERROR_MESSAGE); 
        }
    }

    private void btnExportarTicketActionPerformed(java.awt.event.ActionEvent evt) {
        String folioTexto = txtFolioCobro.getText().trim();
        String contenidoTicket = txtAreaTicket.getText();
        btnExportarTicket.exportarAHTML(folioTexto, contenidoTicket);
    }

    private void btnActualizarTablaActionPerformed(java.awt.event.ActionEvent evt) {
        tblGeneral.setModel(controlador.generarModeloTablaOrdenes());
    }
}