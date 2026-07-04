package AutoLavadoITO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Orden {
    private int idOrden; // Ahora es un entero (Folio automático)
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private double costoFinal;
    private String observaciones;
    private boolean reservacion;
    private Date fechaReserva; // Fecha proveniente del JCalendar
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ArrayList<Servicio> serviciosAsignados;
    private Duration duracionServicio;

    public Orden(int idOrden, Cliente cliente, Vehiculo vehiculo, boolean reservacion, Date fechaReserva, String observaciones) {
        this.idOrden = idOrden;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.reservacion = reservacion;
        this.fechaReserva = fechaReserva;
        this.observaciones = observaciones;
        this.fechaIngreso = LocalDateTime.now();
        this.serviciosAsignados = new ArrayList<>();
        this.costoFinal = 0.0;
        vehiculo.iniciarServicio();
duracionServicio = Duration.ZERO;
    }

    public void agregarServicio(Servicio s) {
        this.serviciosAsignados.add(s);
        this.costoFinal += s.getPrecio();
    }

    public void registrarSalida() {

    fechaSalida = LocalDateTime.now();

    vehiculo.finalizarServicio();

    duracionServicio =
            Duration.between(fechaIngreso,fechaSalida);
}

    public int getIdOrden() { return idOrden; } // Retorna el Folio
    public Cliente getCliente() { return cliente; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public ArrayList<Servicio> getServiciosAsignados() { return serviciosAsignados; }
    public double getCostoFinal() { return costoFinal; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public Date getFechaReserva() { return fechaReserva; }
    
    public LocalDateTime getFechaIngreso() {
    return fechaIngreso;
}

public Duration getDuracionServicio() {
    return duracionServicio;
}

public String getHoraEntradaTexto(){

    DateTimeFormatter f=
            DateTimeFormatter.ofPattern("HH:mm:ss");

    return fechaIngreso.format(f);

}

public String getHoraSalidaTexto(){

    if(fechaSalida==null)
        return "--";

    DateTimeFormatter f=
            DateTimeFormatter.ofPattern("HH:mm:ss");

    return fechaSalida.format(f);

}

public String getDuracionTexto(){

    long h=duracionServicio.toHours();

    long m=duracionServicio.toMinutes()%60;

    long s=duracionServicio.getSeconds()%60;

    return String.format("%02d:%02d:%02d",h,m,s);

}
}
