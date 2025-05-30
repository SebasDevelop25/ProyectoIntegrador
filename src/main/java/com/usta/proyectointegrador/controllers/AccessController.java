package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.services.RolServices;
import com.usta.proyectointegrador.models.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccessController {

    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        model.addAttribute("error", "login");

        if(error != null) {
            model.addAttribute("error", "The username or password is incorrect.");
        }

        if(logout != null) {
            model.addAttribute("msg", "You have been logged out successfully.");
        }

        return "/index";
    }




/*------------------------------------------------------------------------------------------*/
    @GetMapping("/iniciarSesion")
    public String mostrarFormularioIniciarSesion() {
        return "/registro/login";
    }

/*------------------------------------------------------------------------------------------*/
    @GetMapping("/registrarse")
    public String mostrarFormularioRegistro() {
        return "/registro/seleccionRol";
    }

}

