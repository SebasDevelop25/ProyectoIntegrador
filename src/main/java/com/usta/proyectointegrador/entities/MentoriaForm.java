package com.usta.proyectointegrador.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MentoriaForm {
    private Long idMentoria;
    private Integer startupId;
    private BigDecimal monto;
    private LocalDate fecha;

    public Long getIdMentoria() {
        return idMentoria;
    }
    public void setIdMentoria(Long idMentoria) {
        this.idMentoria = idMentoria;
    }
    public Integer getStartupId() {
        return startupId;
    }
    public void setStartupId(Integer startupId) {
        this.startupId = startupId;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
