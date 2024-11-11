package model;

import java.sql.Timestamp;

public class Reserva {
    private int id;
    private int usuarioId;
    private int funcionId;
    private int cantidadBoletos;
    private Timestamp fechaReserva;

    public Reserva(int id, int usuarioId, int funcionId, int cantidadBoletos, Timestamp fechaReserva) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.funcionId = funcionId;
        this.cantidadBoletos = cantidadBoletos;
        this.fechaReserva = fechaReserva;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getFuncionId() { return funcionId; }
    public void setFuncionId(int funcionId) { this.funcionId = funcionId; }

    public int getCantidadBoletos() { return cantidadBoletos; }
    public void setCantidadBoletos(int cantidadBoletos) { this.cantidadBoletos = cantidadBoletos; }

    public Timestamp getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(Timestamp fechaReserva) { this.fechaReserva = fechaReserva; }
}
