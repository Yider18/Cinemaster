package model;

import java.math.BigDecimal;

public class Estadistica {
    private int id;                   // ID de la estadística
    private Integer peliculaId;       // ID de la película (opcional)
    private Integer funcionId;        // ID de la función (opcional)
    private int reservasRealizadas;   // Número de reservas realizadas
    private BigDecimal ingresosTotales; // Ingresos totales generados

    // Constructor general para estadísticas específicas y generales
    public Estadistica(int id, Integer peliculaId, Integer funcionId, int reservasRealizadas, BigDecimal ingresosTotales) {
        this.id = id;
        this.peliculaId = peliculaId;
        this.funcionId = funcionId;
        this.reservasRealizadas = reservasRealizadas;
        this.ingresosTotales = ingresosTotales;
    }

    // Constructor simplificado, sin `id` para nuevas estadísticas
    public Estadistica(Integer peliculaId, Integer funcionId, int reservasRealizadas, BigDecimal ingresosTotales) {
        this.peliculaId = peliculaId;
        this.funcionId = funcionId;
        this.reservasRealizadas = reservasRealizadas;
        this.ingresosTotales = ingresosTotales;
    }

    // Constructor específico para estadísticas generales sin `peliculaId` ni `funcionId`
    public Estadistica(int reservasRealizadas, BigDecimal ingresosTotales) {
        this(null, null, reservasRealizadas, ingresosTotales);
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Integer peliculaId) {
        this.peliculaId = peliculaId;
    }

    public Integer getFuncionId() {
        return funcionId;
    }

    public void setFuncionId(Integer funcionId) {
        this.funcionId = funcionId;
    }

    public int getReservasRealizadas() {
        return reservasRealizadas;
    }

    public void setReservasRealizadas(int reservasRealizadas) {
        this.reservasRealizadas = reservasRealizadas;
    }

    public BigDecimal getIngresosTotales() {
        return ingresosTotales;
    }

    public void setIngresosTotales(BigDecimal ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }

    // Método para determinar si es una estadística general o específica de función
    public boolean isEstadisticaGeneral() {
        return funcionId == null && peliculaId == null;
    }
}
