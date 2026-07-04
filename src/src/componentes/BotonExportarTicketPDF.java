package componentes;

import java.awt.Cursor;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Componente reutilizable: botón que genera un ticket en formato HTML
 * plasmando exactamente el contenido textual generado por el sistema.
 */
public class BotonExportarTicketPDF extends JButton {

    public BotonExportarTicketPDF() {
        super("Imprimir Ticket");

        try {
            java.net.URL rutaIcono = getClass().getResource("/imagenes/bloc_notas.png");
            if (rutaIcono != null) {
                this.setIcon(new ImageIcon(rutaIcono));
            }
        } catch (Exception e) {
            // El botón sigue funcionando si no hay icono
        }

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Genera un archivo HTML imprimiendo textualmente el contenido del JTextArea.
     * * @param folio           Folio de la orden para nombrar el archivo.
     * @param contenidoTicket El String completo del ticket (toString del objeto Ticket).
     */
    public void exportarAHTML(String folio, String contenidoTicket) {

        if (folio == null || folio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se puede exportar: Falta el Folio de la orden.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (contenidoTicket == null || contenidoTicket.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay ningún ticket generado en el área de texto para imprimir.",
                    "Ticket vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreArchivo = "ticket_" + folio.trim() + ".html";

        try (FileWriter fw = new FileWriter(nombreArchivo);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset=\"UTF-8\">");
            pw.println("<title>Ticket " + folio.trim() + "</title>");
            pw.println("<style>");
            pw.println("  body { width: 300px; font-family: 'Courier New', monospace; margin: 0 auto; padding: 10px; }");
            pw.println("  pre { font-family: 'Courier New', monospace; font-size: 12px; white-space: pre-wrap; margin: 0; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            
            // La etiqueta <pre> respeta textualmente los espacios y saltos de línea del toString()
            pw.println("<pre>");
            pw.println(contenidoTicket);
            pw.println("</pre>");
            
            pw.println("</body>");
            pw.println("</html>");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al generar el archivo del ticket: " + e.getMessage(),
                    "Error de escritura",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- Apertura automática en el navegador ---
        try {
            File archivoTicket = new File(nombreArchivo);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivoTicket.toURI());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "El ticket se guardó, pero no se pudo abrir automáticamente: " + e.getMessage(),
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}