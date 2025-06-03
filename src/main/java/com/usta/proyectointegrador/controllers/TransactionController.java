package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.StartupEntity;
import com.usta.proyectointegrador.entities.TransactionEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.services.RolService;
import com.usta.proyectointegrador.models.services.StartupServices;
import com.usta.proyectointegrador.models.services.TransactionServices;
import com.usta.proyectointegrador.models.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    @Autowired
    private TransactionServices transactionRepository;

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private StartupServices startupServices;

    @GetMapping(value = "/invertirStartup/{id}")
    public String InvertirStartups(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("title", "Startups");
        model.addAttribute("urlCreate", "/invertirStartups");

        StartupEntity startupSeleccionada = startupServices.findById(id);
        TransactionEntity nuevaTransaction = new TransactionEntity();
        model.addAttribute("startup", startupSeleccionada);
        model.addAttribute("transaction", nuevaTransaction);

        return "Startups/invertirStartups";
    }

    @GetMapping(value = "/infoInvStartupss")
    public String ListarStartupprueba(Model model) {
        model.addAttribute("title", "Startups");
        model.addAttribute("urlCreate", "/ListarConvoDis2");
        List<StartupEntity> lista = startupServices.findAll();
        lista.sort(Comparator.comparing(StartupEntity::getId_startup));
        model.addAttribute("startups", lista);
        return "inversor/informacionInvStartups";
    }

    @PostMapping("/guardar")
    public String guardarTransaccion(@ModelAttribute("transaction") TransactionEntity transaction,
                                     @RequestParam("startupId") Integer idStartup) {
        StartupEntity Startup = startupServices.findById(idStartup);


        transaction.setStartup(Startup);

        transactionRepository.save(transaction);
        return "redirect:/infoInvStartupss";
    }

    @GetMapping("/transacciones")
    public String listarTransacciones(Model model) {
        List<TransactionEntity> transacciones = transactionRepository.findAll();
        model.addAttribute("transacciones", transacciones);
        return "inversor/listarInversiones"; // Apunta a templates/transacciones/list.html
    }
}
