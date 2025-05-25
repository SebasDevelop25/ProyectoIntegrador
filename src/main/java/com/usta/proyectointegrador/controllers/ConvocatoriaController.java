package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.models.services.ConvocatoriaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
public class ConvocatoriaController {
    @Autowired
    private ConvocatoriaServices convocatoriaServices;

    @GetMapping(value = "/convocatorias")
    public String ListarConvo(Model model) {
        model.addAttribute("title", "Convocatorias");
        model.addAttribute("urlCreate", "/createConvocatoria");
        List<ConvocatoriaEntity> lista = convocatoriaServices.findAll();
        lista.sort(Comparator.comparing(ConvocatoriaEntity::getId_Convocatoria));
        model.addAttribute("convocatorias", lista);
        return "Convocatorias/Convocatorias";
    }

    @GetMapping(value = "/createConvocatoria")
    public String crearConvocatoria(Model model) {
        model.addAttribute("title", "Crear Convocatoria");
        model.addAttribute("convocatoria", new ConvocatoriaEntity());
        return "Convocatorias/ConvocatoriaCrear";

    }

    @PostMapping(value="/crearConvocatoria")
    public String guardarConvocatoria(@Valid ConvocatoriaEntity convocatoria, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "Convocatorias/ConvocatoriaCrear";

        }

        convocatoriaServices.save(convocatoria);
        return "redirect:/convocatorias";

    }
}
