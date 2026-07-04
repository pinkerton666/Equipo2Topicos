package AutoLavadoITO;

import java.time.LocalDateTime;

public class Ticket {
    private Orden ordenAsociada;
    private LocalDateTime fechaHoraTicket;
    private double pagoRecibido;
    private String tipoPago; 
    private double costoFinal;

    public Ticket(Orden ordenAsociada, double pagoRecibido, String tipoPago) {
        this.ordenAsociada = ordenAsociada;
        this.fechaHoraTicket = LocalDateTime.now();
        this.pagoRecibido = pagoRecibido;
        this.tipoPago = tipoPago;
        this.costoFinal = ordenAsociada.getCostoFinal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==== TICKET LAVA AUTOS ====\n");
        sb.append("Fecha: ").append(fechaHoraTicket).append("\n");
        sb.append("Folio de Orden: ").append(ordenAsociada.getIdOrden()).append("\n");
        sb.append("Cliente: ").append(ordenAsociada.getCliente().getNombre()).append("\n");
        sb.append("Vehículo: ").append(ordenAsociada.getVehiculo().getMarca()).append(" (ID: ").append(ordenAsociada.getVehiculo().getIdAuto()).append(")\n");
        
sb.append("Hora Entrada : ");
sb.append(ordenAsociada.getHoraEntradaTexto());

sb.append("\n");

sb.append("Hora Salida : ");
sb.append(ordenAsociada.getHoraSalidaTexto());

sb.append("\n");

sb.append("Duración : ");
sb.append(ordenAsociada.getDuracionTexto());

sb.append("\n");
sb.append("\n");
        sb.append("--- Servicios Aplicados ---\n");
        if(ordenAsociada.getServiciosAsignados().isEmpty()){
            sb.append("- Ninguno\n");
        } else {
            for (Servicio s : ordenAsociada.getServiciosAsignados()) {
                sb.append("- ").append(s.getNombre()).append(" : $").append(s.getPrecio()).append("\n");
            }
        }
        sb.append("----------------------------\n");
        sb.append("Total a Pagar: $").append(costoFinal).append("\n");
        sb.append("Pagó con: $").append(pagoRecibido).append(" (").append(tipoPago).append(")\n");
        sb.append("Cambio: $").append(pagoRecibido >= costoFinal ? (pagoRecibido - costoFinal) : 0.0).append("\n");
        return sb.toString();
    }
}