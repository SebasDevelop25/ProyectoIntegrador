package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.services.RolServices;
import com.usta.proyectointegrador.models.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UsersController {
    @Autowired
    private UsersServices usersServices;
    @Autowired
    private RolServices rolServices;

    @GetMapping("/seleccionarRol")
    public String mostrarRoles(Model model) {
        return "/registro/seleccionRol";
    }

    /*-------------------------------------------------------------------------------------------*/
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(@RequestParam("rol") Integer idNombre, Model model) {
        RolEntity rol = rolServices.findById(idNombre);
        UsersEntity usuario = new UsersEntity();
        usuario.setIdRol(rol);
        model.addAttribute("usuario", usuario);
        model.addAttribute("title", "Register a new user");
        return "/registro/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") @Valid UsersEntity usuario,
                                   BindingResult result,
                                   @RequestParam("confirmarClave") String confirmarClave,
                                   Model model, RedirectAttributes redirectAttributes,
                                   SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Register a new user");
            return "/registro/registro";
        }

        if (!usuario.getClave().equals(confirmarClave)) {
            result.rejectValue("clave", "error.usuario", "The passwords do not match.");
            model.addAttribute("title", "Register a new User");
            return "registro/registro";
        }

        String pass = new BCryptPasswordEncoder().encode(usuario.getClave());
        usuario.setClave(pass);

        RolEntity rol = rolServices.findById(usuario.getIdRol().getIdRol());
        usuario.setIdRol(rol);
        usersServices.save(usuario);
        status.setComplete();
        redirectAttributes.addFlashAttribute("success", "User registered successfully!");
        return "/registro/login";
    }

    /*-------------------------------------------------------------------------------------------*/

    @GetMapping("/indexMentor")
    public String mostrarInterfazMentor() {
        return "/mentor/indexMentor";
    }

    @GetMapping("/startupsAsignadas")
    public String mostrarStarupsAsignadas() {
        return "/mentor/startupsAsignadas";
    }
}
