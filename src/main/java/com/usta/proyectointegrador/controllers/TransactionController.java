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
import java.security.Principal;
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

    @PostMapping("/transacciones/eliminar/{id}")
    public String eliminarTransaccion(@PathVariable("id") Integer idTransaction,
                                      RedirectAttributes redirectAttrs) {
        // 1) Intentamos borrar la transacción
        transactionRepository.deleteById(idTransaction);

        // 2) Opcional: podemos agregar un mensaje flash para notificar al usuario
        redirectAttrs.addFlashAttribute("mensajeExito", "Transacción eliminada correctamente.");

        // 3) Redirigimos de vuelta al listado de transacciones
        return "redirect:/transacciones";
    }

    @GetMapping("/inv")
    public String listarInversionesDeMisStartups(Model model, Principal principal) {
        // 1) Obtenemos el email del usuario autenticado
        String emailUsuario = principal.getName();

        // 2) Buscamos el objeto UsersEntity correspondiente a ese email
        UsersEntity usuario = usersServices.findByEmail(emailUsuario);
        if (usuario == null) {
            // Si por algún motivo no existe, redirige al login (o a otra página de “home”)
            return "redirect:/login";
        }

        // 3) Sacamos el idUsuario del emprendedor
        Long idEmprendedor = usuario.getIdUsuario();

        // 4) Llamamos al servicio para traer todas las transacciones de sus startups
        List<TransactionEntity> inversiones = transactionRepository.findByStartupUsuarioId(idEmprendedor);

        // 5) Enviamos la lista al modelo
        model.addAttribute("transacciones", inversiones);
        model.addAttribute("titulo", "Inversiones en Mis Startups");

        // 6) Devolvemos la plantilla Thymeleaf
        //    (asegúrate de tener src/main/resources/templates/emprendedor/ListarInversiones.html)
        return "emprendedor/ListarInversiones";
    }
}
