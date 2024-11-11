package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class Funcion {
    private int id;
    private int peliculaId;
    private int salaId;
    private Date fecha;
    private Time hora;
    private BigDecimal precio; 

    public Funcion(int id, int peliculaId, int salaId, Date fecha, Time hora, BigDecimal precio) {
        this.id = id;
        this.peliculaId = peliculaId;
        this.salaId = salaId;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPeliculaId() { return peliculaId; }
    public void setPeliculaId(int peliculaId) { this.peliculaId = peliculaId; }

    public int getSalaId() { return salaId; }
    public void setSalaId(int salaId) { this.salaId = salaId; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}

