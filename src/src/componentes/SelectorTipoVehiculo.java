package componentes;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SelectorTipoVehiculo extends JPanel {

    // Imágenes
    private final ImageIcon iconoSedan =
            new ImageIcon(getClass().getResource("/imagenes/sedan.jpg"));

    private final ImageIcon iconoSUV =
            new ImageIcon(getClass().getResource("/imagenes/suv.jpg"));

    private final ImageIcon iconoCamioneta =
            new ImageIcon(getClass().getResource("/imagenes/camioneta.jpg"));

    private final ImageIcon iconoMoto =
            new ImageIcon(getClass().getResource("/imagenes/moto.jpg"));

    // Estado
    private String tipoSeleccionado = "Sedán";

    // Paneles
    private JPanel panelSedan;
    private JPanel panelSUV;
    private JPanel panelCamioneta;
    private JPanel panelMoto;

    public SelectorTipoVehiculo() {

        setLayout(new GridLayout(1, 4, 10, 10));

        panelSedan = crearPanel("Sedán", iconoSedan);
        panelSUV = crearPanel("SUV", iconoSUV);
        panelCamioneta = crearPanel("Camioneta", iconoCamioneta);
        panelMoto = crearPanel("Moto", iconoMoto);

        agregarEventos(panelSedan, "Sedán");
        agregarEventos(panelSUV, "SUV");
        agregarEventos(panelCamioneta, "Camioneta");
        agregarEventos(panelMoto, "Moto");

        add(panelSedan);
        add(panelSUV);
        add(panelCamioneta);
        add(panelMoto);

        seleccionar(panelSedan, "Sedán");
    }

    private JPanel crearPanel(String texto, ImageIcon icono) {

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(2,1));

        panel.setBackground(Color.WHITE);

        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel lblImagen = new JLabel(icono);
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblImagen);
        panel.add(lblTexto);

        return panel;
    }

    private void agregarEventos(JPanel panel, String tipo) {

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                if (!tipoSeleccionado.equals(tipo)) {
                    panel.setBackground(new Color(220,235,255));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                if (!tipoSeleccionado.equals(tipo)) {
                    panel.setBackground(Color.WHITE);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                seleccionar(panel, tipo);
            }

        });
    }

    private void seleccionar(JPanel panelSeleccionado, String tipo) {

        tipoSeleccionado = tipo;

        JPanel[] paneles = {
            panelSedan,
            panelSUV,
            panelCamioneta,
            panelMoto
        };

        for (JPanel p : paneles) {

            if (p == panelSeleccionado) {

                p.setBackground(new Color(210,255,210));

                p.setBorder(
                        BorderFactory.createLineBorder(
                                Color.GREEN,
                                3));

            } else {

                p.setBackground(Color.WHITE);

                p.setBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY));

            }

        }

    }

    public String getTipoSeleccionado() {
        return tipoSeleccionado;
    }

}
