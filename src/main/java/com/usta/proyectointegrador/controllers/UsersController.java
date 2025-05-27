package com.usta.proyectointegrador.controllers;


import com.usta.proyectointegrador.entities.MentoriaDTO;
import com.usta.proyectointegrador.entities.TransactionEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.services.TransactionServices;
import com.usta.proyectointegrador.models.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class   UsersController {

    @Autowired
    TransactionServices transactionServices;
    @Autowired
    private UsersServices usersServices;

    private static final Long ROL_MENTOR = 2L;

    @GetMapping("/mentorias")
    public String listarMentorias(Model model) {
        // 1) Buscamos todos los usuarios con rol mentor
        List<UsersEntity> mentores = usersServices.findByRol(ROL_MENTOR);

        List<MentoriaDTO> mentorias = new ArrayList<>();

        for (UsersEntity mentor : mentores) {
            List<TransactionEntity> txs = transactionServices.findByUsuarioIdUsuario(mentor.getId_usuario());
            for (TransactionEntity tx : txs) {
                // Cada transacción es una mentoría: mentor + startup
                String nombreMentor = mentor.getNombre_usu() + " " + mentor.getApellido_usu();
                String nombreStartup = tx.getStartup().getNombre_startup();
                mentorias.add(new MentoriaDTO(tx.getIdTransaction(), nombreMentor, nombreStartup));
            }
        }

        model.addAttribute("title", "Listado de Mentorías");
        model.addAttribute("mentorias", mentorias);
        model.addAttribute("urlCreate", "/createMentoria");
        return "Usuarios/ListarMentorias";
    }

//    @GetMapping("/mentorias/editar/{id}")
//    public String editarMentoriaForm(@PathVariable Integer id, Model model) { …}
//
//    @PostMapping("/editarMentoria/{id}")
//    public String actualizarMentoria(@PathVariable Integer id) {}
//
//
//    @GetMapping("/eliminarMentoria/{id}")
//    public String eliminarMentoria(@PathVariable Integer id) {
//    }


}
