package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.StartupEntity;
import com.usta.proyectointegrador.models.services.StartupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class StartupController {
    @Autowired
    StartupServices startupServices;

    @GetMapping(value = "/Startups")
    public String ListarStar(Model model) {
        model.addAttribute("title","Startups de la convocatoria");
        model.addAttribute("urlCreate", "/createStartup");
        List<StartupEntity> listaStar = startupServices.findAll();
        listaStar.sort(Comparator.comparing(StartupEntity:: getId_startup));
        model.addAttribute("Startups", listaStar);
        return "/Startups/ListarStartup";
    }







}
