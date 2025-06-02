package com.usta.proyectointegrador.entities;

import java.time.LocalDate;

public class MentoriaDTO2 {

    private Long idTransaccion;
    private Integer idStartup;
    private String nombreMentor;
    private String nombreStartup;
    private LocalDate fecha;
    private String comentario;

    public MentoriaDTO2(Long idTransaccion, Integer idStartup , String nombreMentor, String nombreStartup,
                        LocalDate fecha, String comentario) {
        this.idTransaccion = idTransaccion;
        this.idStartup = idStartup;
        this.nombreMentor = nombreMentor;
        this.nombreStartup = nombreStartup;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    // Getters y Setters
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Integer getIdStartup() {
        return idStartup;
    }

    public void setIdStartup(Integer idStartup) {
        this.idStartup = idStartup;
    }

    public String getNombreMentor() {
        return nombreMentor;
    }

    public void setNombreMentor(String nombreMentor) {
        this.nombreMentor = nombreMentor;
    }

    public String getNombreStartup() {
        return nombreStartup;
    }

    public void setNombreStartup(String nombreStartup) {
        this.nombreStartup = nombreStartup;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}

