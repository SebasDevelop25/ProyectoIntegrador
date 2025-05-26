package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.UsersEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccessController {

    @GetMapping("/seleccionarRol")
    public String mostrarRoles(Model model) {
        return "/registro/seleccionRol";
    }
    @GetMapping("/registro/registro")
    public String mostrarFormularioRegistro(@RequestParam("rol") String rol, Model model) {
        model.addAttribute("rol", rol);
        model.addAttribute("usuario", new UsersEntity());
        return "/registro/registro";
    }

}
