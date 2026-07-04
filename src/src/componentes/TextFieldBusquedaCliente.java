package componentes;

import AutoLavadoITO.Controlador;
import AutoLavadoITO.Cliente;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class TextFieldBusquedaCliente extends JTextField implements Serializable {

    // Interfaz para notificar a la Vista los resultados encontrados en tiempo real
    public interface OyenteResultadoBusqueda {
        void registrarResultados(ArrayList<Cliente> clientesEncontrados);
    }

    private Controlador controlador;
    private OyenteResultadoBusqueda oyente;
    private ArrayList<Cliente> fuenteClientes; // Referencia directa a la lista del negocio

    public TextFieldBusquedaCliente() {
        super();
        inicializarComponente();
    }

    private void inicializarComponente() {
        // Escucha de manera activa si el usuario escribe, borra o pega texto
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ejecutarFiltrado();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ejecutarFiltrado();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ejecutarFiltrado();
            }
        });
    }

    /**
     * Inyecta las dependencias necesarias desde el Frame Principal.
     */
    public void conectarControlador(Controlador controlador, ArrayList<Cliente> listaOriginal, OyenteResultadoBusqueda oyente) {
        this.controlador = controlador;
        this.fuenteClientes = listaOriginal;
        this.oyente = oyente;
    }

    /**
     * Realiza la búsqueda automática comparando por Nombre o Apellido de manera síncrona.
     */
    private void ejecutarFiltrado() {
        if (oyente == null || fuenteClientes == null) {
            return;
        }

        String patron = this.getText().trim().toLowerCase();
        ArrayList<Cliente> subListaFiltrada = new ArrayList<>();

        // Si el campo está vacío, devolvemos todos los clientes
        if (patron.isEmpty()) {
            oyente.registrarResultados(fuenteClientes);
            return;
        }

        // Algoritmo de coincidencia secuencial
        for (Cliente cliente : fuenteClientes) {
            String nombreCompleto = (cliente.getNombre() + " " + cliente.getApellido()).toLowerCase();
            
            // Evalúa si coincide por nombre, apellido o la unión de ambos
            if (cliente.getNombre().toLowerCase().contains(patron) || 
                cliente.getApellido().toLowerCase().contains(patron) || 
                nombreCompleto.contains(patron)) {
                
                subListaFiltrada.add(cliente);
            }
        }

        // Notifica de forma automática la nueva sublista para refrescar las tablas o etiquetas de la Vista
        oyente.registrarResultados(subListaFiltrada);
    }
}