package AutoLavadoITO;
import java.time.Duration;
import java.time.LocalDateTime;
public class Vehiculo {
    private int idAuto; 
    private String marca;
    private String modelo;
    private String color;
    private String tipo;
    private String observaciones;
    private Cliente propietario;
    
    private LocalDateTime horaEntrada;
private LocalDateTime horaSalida;
private Duration tiempoServicio;
private boolean enServicio;

    public Vehiculo(int idAuto, String marca, String modelo, String color, String tipo, String observaciones, Cliente propietario) {
        this.idAuto = idAuto;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.propietario = propietario;
        horaEntrada = null;
horaSalida = null;
tiempoServicio = Duration.ZERO;
enServicio = false;
    }

    public int getIdAuto() { return idAuto; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getColor() { return color; }
    public String getTipo() { return tipo; }
    public Cliente getPropietario() { return propietario; }
    
    public void iniciarServicio() {
    horaEntrada = LocalDateTime.now();
    enServicio = true;
}

public void finalizarServicio() {
    horaSalida = LocalDateTime.now();

    if(horaEntrada != null){
        tiempoServicio = Duration.between(horaEntrada,horaSalida);
    }

    enServicio = false;
}

public LocalDateTime getHoraEntrada() {
    return horaEntrada;
}

public LocalDateTime getHoraSalida() {
    return horaSalida;
}

public Duration getTiempoServicio() {
    return tiempoServicio;
}

public boolean isEnServicio() {
    return enServicio;
}
}