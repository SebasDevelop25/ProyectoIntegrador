package com.usta.proyectointegrador.entities;

import lombok.Data;

@Data
public class MentoriaDTO3 {


    private Long idTransaccion;
    private Integer idStartup;
    private String nombreMentor;
    private String nombreStartup;
    private String nombreEmprendedor;
    private String nombreConvocatoria;
    private String fotoMentor;
    private String fotoStartup;

    public MentoriaDTO3(Long idTransaccion, Integer idStartup , String nombreMentor, String nombreStartup,
                       String nombreEmprendedor, String nombreConvocatoria,String fotoMentor, String fotoStartup) {
        this.idTransaccion = idTransaccion;
        this.idStartup = idStartup;
        this.nombreMentor = nombreMentor;
        this.nombreStartup = nombreStartup;
        this.nombreEmprendedor = nombreEmprendedor;
        this.nombreConvocatoria = nombreConvocatoria;
        this.fotoMentor = fotoMentor;
        this.fotoStartup = fotoStartup;
    }
}
