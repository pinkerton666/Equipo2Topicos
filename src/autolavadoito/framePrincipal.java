package AutoLavadoITO;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.Calendar;
import java.util.Date;

public class framePrincipal extends javax.swing.JFrame {
    
    private Controlador controlador;
    private javax.swing.JTabbedPane jTabbedPane1;
    
    // Instancia de la clase que pintará el fondo de la ventana
    private FondoPanel fondo = new FondoPanel();
    
    // TAB 1
    private javax.swing.JPanel panelRegistro;
    private javax.swing.JTextField txtNombreCli, txtApellidoCli, txtTelefonoCli;
    private javax.swing.JTextField txtMarca, txtModelo, txtColor, txtObservacionesV;
    private javax.swing.JComboBox<String> cmbTipoVehiculo;
    private javax.swing.JButton btnRegistrarCliYVeh;

    // TAB 2
    private javax.swing.JPanel panelAgregarAuto;
    private javax.swing.JTextField txtIdClienteBuscar;
    private javax.swing.JLabel lblCliEncontrado;
    private javax.swing.JButton btnBuscarCli;
    private javax.swing.JTextField txtMarca2, txtModelo2, txtColor2, txtObservacionesV2;
    private javax.swing.JComboBox<String> cmbTipoVehiculo2;
    private javax.swing.JButton btnAgregarSoloAuto;

    // TAB 3: Órdenes
    private javax.swing.JPanel panelOrdenes;
    private javax.swing.JTextField txtIdClienteOrd, txtIdAutoOrd, txtFolioServicio;
    private javax.swing.JCheckBox chkReserva;
    private com.toedter.calendar.JDateChooser jdFechaReserva;
    private javax.swing.JComboBox<String> cmbServiciosDisponibles;
    private javax.swing.JButton btnCrearOrden, btnAgregarServicio;

    // TAB 4: Salida
    private javax.swing.JPanel panelSalida;
    private javax.swing.JTextField txtFolioCobro, txtMontoPago;
    private javax.swing.JComboBox<String> cmbTipoPago;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JTextArea txtAreaTicket;

    // TAB 5
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tblGeneral;
    private javax.swing.JButton btnActualizarTabla;

    public framePrincipal(Controlador controlador) {
        this.controlador = controlador;
        
        // 1. Establecemos el panel personalizado con la imagen como el contenedor principal
        this.setContentPane(fondo);
        
        // 2. Inicializamos los componentes sobre el nuevo fondo
        initComponents();
        cargarServiciosCombo();
        configurarEventosPago();
        configurarEventosReserva(); 
        btnActualizarTablaActionPerformed(null);
    }

    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        
        // --- TAB 1 ---
        panelRegistro = new javax.swing.JPanel(new java.awt.GridLayout(9, 2, 5, 5));
        txtNombreCli = new javax.swing.JTextField(); txtApellidoCli = new javax.swing.JTextField();
        txtTelefonoCli = new javax.swing.JTextField(); txtMarca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField(); txtColor = new javax.swing.JTextField();
        txtObservacionesV = new javax.swing.JTextField();
        cmbTipoVehiculo = new javax.swing.JComboBox<>(new String[]{"Sedán", "SUV", "Camioneta", "Motocicleta"});
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

        // --- TAB 2 ---
        panelAgregarAuto = new javax.swing.JPanel(new java.awt.GridLayout(9, 2, 5, 5));
        txtIdClienteBuscar = new javax.swing.JTextField();
        btnBuscarCli = new javax.swing.JButton("Buscar Cliente");
        lblCliEncontrado = new javax.swing.JLabel("...");
        txtMarca2 = new javax.swing.JTextField(); txtModelo2 = new javax.swing.JTextField();
        txtColor2 = new javax.swing.JTextField(); txtObservacionesV2 = new javax.swing.JTextField();
        cmbTipoVehiculo2 = new javax.swing.JComboBox<>(new String[]{"Sedán", "SUV", "Camioneta", "Motocicleta"});
        btnAgregarSoloAuto = new javax.swing.JButton("Agregar Auto");

        panelAgregarAuto.add(new javax.swing.JLabel("ID Cliente:")); panelAgregarAuto.add(txtIdClienteBuscar);
        panelAgregarAuto.add(btnBuscarCli); panelAgregarAuto.add(lblCliEncontrado);
        panelAgregarAuto.add(new javax.swing.JLabel("Marca Auto:")); panelAgregarAuto.add(txtMarca2);
        panelAgregarAuto.add(new javax.swing.JLabel("Modelo:")); panelAgregarAuto.add(txtModelo2);
        panelAgregarAuto.add(new javax.swing.JLabel("Color:")); panelAgregarAuto.add(txtColor2);
        panelAgregarAuto.add(new javax.swing.JLabel("Tipo:")); panelAgregarAuto.add(cmbTipoVehiculo2);
        panelAgregarAuto.add(new javax.swing.JLabel("Observaciones:")); panelAgregarAuto.add(txtObservacionesV2);
        panelAgregarAuto.add(new javax.swing.JLabel("")); panelAgregarAuto.add(btnAgregarSoloAuto);
        jTabbedPane1.addTab("2. Auto Nuevo", panelAgregarAuto);

        // --- TAB 3 ---
        panelOrdenes = new javax.swing.JPanel(new java.awt.GridLayout(8, 2, 5, 5));
        txtIdClienteOrd = new javax.swing.JTextField(); txtIdAutoOrd = new javax.swing.JTextField();
        chkReserva = new javax.swing.JCheckBox("¿Es Reserva?");
        jdFechaReserva = new com.toedter.calendar.JDateChooser();
        jdFechaReserva.setEnabled(false); 
        btnCrearOrden = new javax.swing.JButton("1. Crear Orden (Genera Folio)");
        
        txtFolioServicio = new javax.swing.JTextField();
        cmbServiciosDisponibles = new javax.swing.JComboBox<>();
        btnAgregarServicio = new javax.swing.JButton("2. Añadir Servicio al Folio");

        panelOrdenes.add(new javax.swing.JLabel("ID Cliente:")); panelOrdenes.add(txtIdClienteOrd);
        panelOrdenes.add(new javax.swing.JLabel("ID Auto:")); panelOrdenes.add(txtIdAutoOrd);
        panelOrdenes.add(chkReserva); panelOrdenes.add(jdFechaReserva);
        panelOrdenes.add(new javax.swing.JLabel("")); panelOrdenes.add(btnCrearOrden);
        panelOrdenes.add(new javax.swing.JLabel("--- AGREGAR SERVICIOS ---")); panelOrdenes.add(new javax.swing.JLabel("-----------------------"));
        panelOrdenes.add(new javax.swing.JLabel("Ingrese Folio de Orden:")); panelOrdenes.add(txtFolioServicio);
        panelOrdenes.add(new javax.swing.JLabel("Seleccione Servicio:")); panelOrdenes.add(cmbServiciosDisponibles);
        panelOrdenes.add(new javax.swing.JLabel("")); panelOrdenes.add(btnAgregarServicio);
        jTabbedPane1.addTab("3. Generar Orden y Servicios", panelOrdenes);

        // --- TAB 4 ---
        panelSalida = new javax.swing.JPanel(new java.awt.BorderLayout());
        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.GridLayout(4, 2));
        txtFolioCobro = new javax.swing.JTextField();
        txtMontoPago = new javax.swing.JTextField();
        cmbTipoPago = new javax.swing.JComboBox<>(new String[]{"Efectivo", "Tarjeta"});
        btnCobrar = new javax.swing.JButton("Cobrar y Ticket");
        
        topPanel.add(new javax.swing.JLabel("Folio de Orden a Cobrar:")); topPanel.add(txtFolioCobro);
        topPanel.add(new javax.swing.JLabel("Monto Pagado:")); topPanel.add(txtMontoPago);
        topPanel.add(new javax.swing.JLabel("Tipo Pago:")); topPanel.add(cmbTipoPago);
        topPanel.add(new javax.swing.JLabel("")); topPanel.add(btnCobrar);
        
        txtAreaTicket = new javax.swing.JTextArea(15, 30);
        panelSalida.add(topPanel, java.awt.BorderLayout.NORTH);
        panelSalida.add(new javax.swing.JScrollPane(txtAreaTicket), java.awt.BorderLayout.CENTER);
        jTabbedPane1.addTab("4. Cobro y Salida", panelSalida);

        // --- TAB 5 ---
        panelTabla = new javax.swing.JPanel(new java.awt.BorderLayout());
        tblGeneral = new javax.swing.JTable();
        btnActualizarTabla = new javax.swing.JButton("Actualizar Vistas");
        panelTabla.add(new javax.swing.JScrollPane(tblGeneral), java.awt.BorderLayout.CENTER);
        panelTabla.add(btnActualizarTabla, java.awt.BorderLayout.SOUTH);
        jTabbedPane1.addTab("5. Control Interno", panelTabla);

        // Eventos
        btnRegistrarCliYVeh.addActionListener(this::btnRegistrarCliYVehActionPerformed);
        btnBuscarCli.addActionListener(this::btnBuscarCliActionPerformed);
        btnAgregarSoloAuto.addActionListener(this::btnAgregarSoloAutoActionPerformed);
        btnCrearOrden.addActionListener(this::btnCrearOrdenActionPerformed);
        btnAgregarServicio.addActionListener(this::btnAgregarServicioActionPerformed);
        btnCobrar.addActionListener(this::btnCobrarActionPerformed);
        btnActualizarTabla.addActionListener(this::btnActualizarTablaActionPerformed);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(jTabbedPane1);
        setSize(700, 500);
        setLocationRelativeTo(null);
    }

    private void cargarServiciosCombo() {
        for(Servicio s : controlador.getCatalogoServicios()) {
            cmbServiciosDisponibles.addItem(s.getNombre() + " - $" + s.getPrecio());
        }
    }

    private void configurarEventosPago() {
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
            } catch (Exception ex) {
            }
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
        if(!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || !apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            JOptionPane.showMessageDialog(this, "El nombre y apellido no pueden contener números ni signos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!tel.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "El teléfono solo debe contener números.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!tel.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El teléfono solo debe contener 10 digitos w.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String regexAlfaNumerico = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$";
        if(!marca.matches(regexAlfaNumerico) || !modelo.matches(regexAlfaNumerico) || !color.matches(regexAlfaNumerico)) {
            JOptionPane.showMessageDialog(this, "La marca, modelo y color del auto no deben contener signos especiales.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idCli = controlador.crearCliente(nombre, apellido, tel);
        if (idCli != -1) {
            int idAuto = controlador.registrarVehiculo(idCli, marca, modelo, color, cmbTipoVehiculo.getSelectedItem().toString(), txtObservacionesV.getText());
            JOptionPane.showMessageDialog(this, "¡Éxito!\nTu ID Cliente es: " + idCli + "\nEl ID de Auto es: " + idAuto);
            txtNombreCli.setText(""); txtApellidoCli.setText(""); txtTelefonoCli.setText(""); txtMarca.setText(""); txtModelo.setText(""); txtColor.setText(""); txtObservacionesV.setText("");
        }
    }

    private void btnBuscarCliActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Cliente c = controlador.buscarClientePorId(Integer.parseInt(txtIdClienteBuscar.getText().trim()));
            if(c != null) lblCliEncontrado.setText("Cliente: " + c.getNombre() + " " + c.getApellido());
            else lblCliEncontrado.setText("No encontrado.");
        } catch(Exception e) { JOptionPane.showMessageDialog(this, "Ingrese un ID numérico válido."); }
    }

    private void btnAgregarSoloAutoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int idCli = Integer.parseInt(txtIdClienteBuscar.getText().trim());
            String marca = txtMarca2.getText().trim();
            String modelo = txtModelo2.getText().trim();
            String color = txtColor2.getText().trim();
            String obs = txtObservacionesV2.getText().trim();

            if(marca.isEmpty() || modelo.isEmpty() || color.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Marca, Modelo y Color son obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String regexAlfaNumerico = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$";
            if(!marca.matches(regexAlfaNumerico) || !modelo.matches(regexAlfaNumerico) || !color.matches(regexAlfaNumerico)) {
                JOptionPane.showMessageDialog(this, "La marca, modelo y color no deben contener signos especiales.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idAuto = controlador.registrarVehiculo(idCli, marca, modelo, color, cmbTipoVehiculo2.getSelectedItem().toString(), obs);
            if(idAuto != -1) {
                JOptionPane.showMessageDialog(this, "Auto agregado.\nEl ID de este Auto es: " + idAuto);
                txtMarca2.setText(""); txtModelo2.setText(""); txtColor2.setText(""); txtObservacionesV2.setText("");
            } else { JOptionPane.showMessageDialog(this, "No se encontró cliente."); }
        } catch(Exception e) { JOptionPane.showMessageDialog(this, "ID inválido o campos vacíos.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void btnCrearOrdenActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Vehiculo v = controlador.buscarVehiculoPorIds(Integer.parseInt(txtIdClienteOrd.getText()), Integer.parseInt(txtIdAutoOrd.getText()));
            if(v != null) {
                java.util.Date fecha = null;
                if(chkReserva.isSelected()) {
                    fecha = jdFechaReserva.getDate();
                    if(fecha == null) {
                        JOptionPane.showMessageDialog(this, "Por favor seleccione una fecha en el calendario.");
                        return;
                    }

                    Calendar calHoy = Calendar.getInstance();
                    calHoy.set(Calendar.HOUR_OF_DAY, 0);
                    calHoy.set(Calendar.MINUTE, 0);
                    calHoy.set(Calendar.SECOND, 0);
                    calHoy.set(Calendar.MILLISECOND, 0);
                    Date hoy = calHoy.getTime();

                    if(fecha.before(hoy)) {
                        JOptionPane.showMessageDialog(this, "No se permiten fechas anteriores al día de hoy.", "Fecha Inválida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                int folioGen = controlador.crearOrden(v, chkReserva.isSelected(), fecha, "");
                
                JOptionPane.showMessageDialog(this, "ORDEN CREADA EXITOSAMENTE.\n\n** TU FOLIO ES: " + folioGen + " **\n\nUsa este número para añadir servicios y cobrar.");
                
                txtFolioServicio.setText(String.valueOf(folioGen));
                txtFolioCobro.setText(String.valueOf(folioGen));
                
            } else { JOptionPane.showMessageDialog(this, "Vehículo o Cliente no encontrado."); }
        } catch(Exception e) { JOptionPane.showMessageDialog(this, "Revisa los IDs.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void btnAgregarServicioActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int folio = Integer.parseInt(txtFolioServicio.getText().trim());
            Orden o = controlador.buscarOrdenPorFolio(folio);
            
            if (o != null) {
                o.agregarServicio(controlador.getCatalogoServicios().get(cmbServiciosDisponibles.getSelectedIndex()));
                JOptionPane.showMessageDialog(this, "Servicio añadido a la Orden #" + folio + ".\nTotal a pagar acumulado: $" + o.getCostoFinal());
            } else { JOptionPane.showMessageDialog(this, "No hay una orden activa con el Folio: " + folio); }
        } catch(Exception e) { JOptionPane.showMessageDialog(this, "Por favor ingrese un Folio válido."); }
    }

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int folio = Integer.parseInt(txtFolioCobro.getText().trim());
            double pago = Double.parseDouble(txtMontoPago.getText().trim());
            String tipoPago = cmbTipoPago.getSelectedItem().toString();
            
            Orden o = controlador.buscarOrdenPorFolio(folio);
            if (o == null) {
                JOptionPane.showMessageDialog(this, "La orden específica no existe.", "Error", JOptionPane.ERROR_MESSAGE);
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
            } else { JOptionPane.showMessageDialog(this, "Orden #" + folio + " ya fue cobrada."); }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Folio o monto inválido.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void btnActualizarTablaActionPerformed(java.awt.event.ActionEvent evt) {
        tblGeneral.setModel(controlador.generarModeloTablaOrdenes());
    }
}

// CLASE INTERNA PARA DIBUJAR LA IMAGEN COMO FONDO DEL JFRAME
class FondoPanel extends javax.swing.JPanel {
    private java.awt.Image imagen;

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        java.net.URL url = getClass().getResource("/imagenes/anuncio.jfif");
        if (url != null) {
            imagen = new javax.swing.ImageIcon(url).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}