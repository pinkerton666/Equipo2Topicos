package AutoLavadoITO;

import java.util.ArrayList;

public class Cliente {
    private int idCliente; 
    private String nombre;
    private String apellido;
    private String telefono;
    private ArrayList<Vehiculo> listaVehiculos; 

    public Cliente(int idCliente, String nombre, String apellido, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.listaVehiculos = new ArrayList<>();
    }

    public int getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public ArrayList<Vehiculo> getListaVehiculos() { return listaVehiculos; }
    
    public void agregarVehiculo(Vehiculo v) { this.listaVehiculos.add(v); }
}