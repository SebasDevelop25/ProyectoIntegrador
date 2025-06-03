package com.usta.proyectointegrador.entities;

import lombok.Data;

@Data
public class MentoriaDTO {

    private Long idTransaccion;
    private Integer idStartup;
    private String nombreMentor;
    private String nombreStartup;
    private String nombreEmprendedor;
    private String nombreConvocatoria;
    private String logoStartup;

    public MentoriaDTO(Long idTransaccion, Integer idStartup ,String nombreMentor, String nombreStartup,
                       String nombreEmprendedor, String nombreConvocatoria, String logoStartup) {
        this.idTransaccion = idTransaccion;
        this.idStartup = idStartup;
        this.nombreMentor = nombreMentor;
        this.nombreStartup = nombreStartup;
        this.nombreEmprendedor = nombreEmprendedor;
        this.nombreConvocatoria = nombreConvocatoria;
        this.logoStartup = logoStartup;
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

    public String getNombreEmprendedor() {
        return nombreEmprendedor;
    }

    public void setNombreEmprendedor(String nombreEmprendedor) {
        this.nombreEmprendedor = nombreEmprendedor;
    }

    public String getNombreConvocatoria() {
        return nombreConvocatoria;
    }

    public void setNombreConvocatoria(String nombreConvocatoria) {
        this.nombreConvocatoria = nombreConvocatoria;
    }

    public String getLogoStartup() { return logoStartup; }

    public void setLogoStartup(String logoStartup) { this.logoStartup = logoStartup; }

}

