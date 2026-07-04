package AutoLavadoITO;

public class Servicio {
    private String idServicio;
    private String nombre; 
    private double precio;
    private int duracion; 
    private String observaciones;

    public Servicio(String idServicio, String nombre, double precio, int duracion, String observaciones) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
        this.observaciones = observaciones;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
}
