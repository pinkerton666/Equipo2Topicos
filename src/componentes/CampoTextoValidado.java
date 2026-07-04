package componentes;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class CampoTextoValidado extends JTextField {

    private String tipoValidacion = "LETRAS";
    private boolean correcto = false;

    public CampoTextoValidado() {

        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                validarContenido();
            }

        });

    }

    private void validarContenido() {

        String texto = getText().trim();

        if(texto.isEmpty()){

            correcto = false;
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            return;

        }

        String regex;

        switch(tipoValidacion.toUpperCase()){

            case "TELEFONO":

                regex="\\d{10}";
                break;

            case "PLACAS":

                regex="[A-Z]{3}-\\d{3}-[A-Z]";
                break;

            case "MARCA":

                regex="[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+";
                break;

            case "MODELO":

                regex="[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+";
                break;

            case "COLOR":

                regex="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+";
                break;
 case "Observaciones":

                regex="[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+";
                break;
            default:

                regex="[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+";

        }

        correcto = texto.matches(regex);

        if(correcto){

            setBorder(BorderFactory.createLineBorder(Color.GREEN,2));

        }else{

            setBorder(BorderFactory.createLineBorder(Color.RED,2));

        }

    }

    public String getTipoValidacion() {
        return tipoValidacion;
    }

    public void setTipoValidacion(String tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
        validarContenido();
    }

    public boolean isCorrecto() {
        return correcto;
    }

}