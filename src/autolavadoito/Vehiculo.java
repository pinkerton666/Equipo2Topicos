package AutoLavadoITO;

public class Vehiculo {
    private int idAuto; 
    private String marca;
    private String modelo;
    private String color;
    private String tipo;
    private String observaciones;
    private Cliente propietario;

    public Vehiculo(int idAuto, String marca, String modelo, String color, String tipo, String observaciones, Cliente propietario) {
        this.idAuto = idAuto;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.propietario = propietario;
    }

    public int getIdAuto() { return idAuto; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getColor() { return color; }
    public String getTipo() { return tipo; }
    public Cliente getPropietario() { return propietario; }
}