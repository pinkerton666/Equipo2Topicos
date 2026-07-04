package componentes;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.util.Calendar;

/**
 * Componente Custom Java Bean: BannerBienvenida (Versión Transparente)
 */
public class BannerBienvenida extends JPanel {

    private JLabel lblSaludo;

    public BannerBienvenida() {
        super();
        // Hacemos el panel invisible para que se aprecie la imagen de fondo de la app
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 18));
        
        lblSaludo = new JLabel();
        // Usamos una tipografía más moderna y limpia (Segoe UI o Montserrat si está disponible)
        lblSaludo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        this.add(lblSaludo);
        establecerSaludoPorHora();
    }

    private void establecerSaludoPorHora() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);

        // En lugar de fondos de colores pesados, usamos texto blanco brillante 
        // con un ligero contraste que resalta sobre cualquier imagen
        lblSaludo.setForeground(Color.WHITE);

        if (hora >= 6 && hora < 12) {
            lblSaludo.setText(" ¡Buenos días, operador! Listo para la jornada.");
        } else if (hora >= 12 && hora < 19) {
            lblSaludo.setText("¡Buenas tardes, operador! Mantengamos el ritmo.");
        } else {
            lblSaludo.setText("¡Buenas noches, operador! Buen cierre de turno.");
        }
    }
}