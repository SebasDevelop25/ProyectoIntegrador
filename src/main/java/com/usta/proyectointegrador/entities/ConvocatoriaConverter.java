package com.usta.proyectointegrador.entities;

import com.usta.proyectointegrador.models.services.ConvocatoriaServices;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConvocatoriaConverter implements Converter<String, ConvocatoriaEntity> {

    private final ConvocatoriaServices convocatoriaService;

    public ConvocatoriaConverter(ConvocatoriaServices convocatoriaService) {
        this.convocatoriaService = convocatoriaService;
    }

    @Override
    public ConvocatoriaEntity convert(String id) {
        try {
            Long idConvocatoria = Long.parseLong(id);
            return convocatoriaService.findById(idConvocatoria);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

