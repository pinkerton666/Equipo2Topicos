package AutoLavadoITO;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class FondoPanel extends JPanel {
    private Image imagen;

    @Override
    public void paint(Graphics g) {
        // Cambia la ruta por la de tu imagen
        imagen = new ImageIcon(getClass().getResource("/imagenes/images (1).jfif")).getImage();
        
        // Dibuja la imagen en todo el tamaño del panel
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        
        setOpaque(false); // Hace el panel transparente para ver los componentes
        super.paint(g);
    }
}