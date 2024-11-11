package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Factura {
    private int id;                   // ID de la factura
    private int reservaId;            // ID de la reserva asociada
    private BigDecimal montoTotal;    // Monto total de la factura, calculado automáticamente
    private Timestamp fechaFactura;   // Fecha de la factura

    // Constructor completo, usado para cargar facturas desde la base de datos
    public Factura(int id, int reservaId, BigDecimal montoTotal, Timestamp fechaFactura) {
        this.id = id;
        this.reservaId = reservaId;
        this.montoTotal = montoTotal;
        this.fechaFactura = fechaFactura;
    }

    // Constructor simplificado, usado cuando se crea una nueva factura
    public Factura(int reservaId, Timestamp fechaFactura) {
        this.reservaId = reservaId;
        this.fechaFactura = fechaFactura;
        this.montoTotal = null; // Se calcula y asigna en el controlador o DAO
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Timestamp getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Timestamp fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
}
