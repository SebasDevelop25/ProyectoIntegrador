package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.StartupEntity;
import com.usta.proyectointegrador.models.services.ConvocatoriaServices;
import com.usta.proyectointegrador.models.services.StartupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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


    @PostMapping(value = "/crearConvocatoria")
    public String guardarConvocatoria(@Valid ConvocatoriaEntity convocatoria, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "Convocatorias/ConvocatoriaCrear";

        }

        convocatoriaServices.save(convocatoria);
        redirectAttributes.addFlashAttribute("mensaje", "Convocatoria creada");
        return "redirect:/convocatorias";

    }

    @GetMapping(value = "/editarConvocatoria/{id}")
    public String editarConvocatoria(@PathVariable(value = "id") Long idConvo, Model model) {
        ConvocatoriaEntity convocatoria = convocatoriaServices.findById(idConvo);
        model.addAttribute("title", "Editar Convocatoria");
        model.addAttribute("convocatoriaEdit", convocatoria);
        return "Convocatorias/ConvocatoriasEdit";
    }

    @PostMapping(value = "/editarConvocatoria/{id}")
    public String editarConvocatoria(@ModelAttribute("convocatoriaEdit") ConvocatoriaEntity convocatoria,
                                     @PathVariable(value = "id") Long idConvo,
                                     RedirectAttributes redirectAttributes,
                                     BindingResult result) throws IOException {
        ConvocatoriaEntity convoAux = convocatoriaServices.findById(idConvo);
        convoAux.setId_Convocatoria(idConvo);
        convoAux.setTitleConvocatoria(convocatoria.getTitleConvocatoria());
        convoAux.setId_Convocatoria(convocatoria.getId_Convocatoria());
        convoAux.setFechaFin(convocatoria.getFechaFin());
        convoAux.setFechaInicio(convocatoria.getFechaInicio());

        convocatoriaServices.actualizar(convoAux);
        redirectAttributes.addFlashAttribute("mensaje", "Convocatoria actualizada");
        return "redirect:/convocatorias";

    }

    @PostMapping(value = "/eliminarConvo/{id}")
    public String eliminarCon(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        if (id > 0) {
            ConvocatoriaEntity convo = convocatoriaServices.findById(id);
            if (convo != null) {
                convocatoriaServices.deleteById(id);
                redirectAttributes.addFlashAttribute("mensaje", "Convocatoria eliminada");
            }
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "No se puede eliminar, id invalido");
        }
        return "redirect:/convocatorias";
    }


}

