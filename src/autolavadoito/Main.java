package AutoLavadoITO;

public class Main {
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new framePrincipal(controlador).setVisible(true);
            }
        });
    }
}
