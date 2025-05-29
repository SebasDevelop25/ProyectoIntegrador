package com.usta.proyectointegrador.entities;

public class MentoriaDTO {

    private Long idTransaccion;
    private String nombreMentor;
    private String nombreStartup;

    public MentoriaDTO(Long idTransaccion,String nombreMentor, String nombreStartup) {
        this.idTransaccion = idTransaccion;
        this.nombreMentor = nombreMentor;
        this.nombreStartup = nombreStartup;
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
    public Long getIdTransaccion() {
        return idTransaccion;
    }
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

}
