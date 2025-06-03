package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.*;
import com.usta.proyectointegrador.models.dao.NotificacionDAO;
import com.usta.proyectointegrador.models.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.services.RolServices;
import com.usta.proyectointegrador.models.services.UsersServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

    @Autowired
    TransactionServices transactionServices;
    @Autowired
    private UsersServices usersServices;
    @Autowired
    private StartupServices startupServices;
    @Autowired
    private RolService rolService;


    private static final Long ROL_MENTOR = 3L;
    @Autowired
    private NotificacionDAO notificacionDAO;

    @GetMapping("/mentorias")
    public String listarMentorias(Model model) {
        List<UsersEntity> mentores = usersServices.findByRol(ROL_MENTOR);
        List<MentoriaDTO> mentorias = new ArrayList<>();

        System.out.println("Mentores encontrados: " + mentores.size());

        for (UsersEntity mentor : mentores) {
            List<TransactionEntity> txs = transactionServices.findByUsuarioIdUsuario
                    (mentor.getIdUsuario());
            for (TransactionEntity tx : txs) {
                String nombreMentor = mentor.getNombre_usu() + " " + mentor.getApellido_usu();
                String nombreStartup = tx.getStartup().getNombre_startup();

                // Extraigo el emprendedor sin cambios:
                String nombreEmprendedor = tx.getNombreUsu().getNombre_usu();

                // Aquí chequeo que exista Convocatoria antes de llamar a getTitleConvocatoria()
                ConvocatoriaEntity convocatoria = tx.getStartup().getConvocatoria();
                String nombreConvocatoria = (convocatoria != null)
                        ? convocatoria.getTitleConvocatoria()
                        : "Sin Convocatoria";
                String logo =  tx.getStartup().getLogo();
                mentorias.add(new MentoriaDTO(
                        tx.getIdTransaction(),
                        tx.getStartup().getId_startup(),
                        nombreMentor,
                        nombreStartup,
                        nombreEmprendedor,
                        nombreConvocatoria,
                        logo
                ));
            }
        }

        model.addAttribute("title", "Listado de Mentorías");
        model.addAttribute("mentorias", mentorias);
        model.addAttribute("urlCreate", "/createMentoria");
        return "Usuarios/ListarMentorias";
    }



    @GetMapping("/createMentoria")
    public String crearMentoria(Model model) {
        model.addAttribute("title", "Crear Mentoria");
        model.addAttribute("mentoriaForm", new MentoriaForm());
        model.addAttribute("mentores", usersServices.findByRol(ROL_MENTOR));
        model.addAttribute("startups", startupServices.findAll());
        return "Usuarios/CrearMentoria";
    }

    @PostMapping("/crearMentoria")
    public String crearMentoria(@Valid MentoriaForm mentoria,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("mentores", usersServices.findByRol(ROL_MENTOR));
            model.addAttribute("startups", startupServices.findAll());
            return "Usuarios/CrearMentoria";
        }

        UsersEntity mentor = usersServices.findById(mentoria.getIdMentoria());
        StartupEntity startup = startupServices.findById(mentoria.getStartupId());

        TransactionEntity transaction = new TransactionEntity();
        transaction.setNombreUsu(mentor);
        transaction.setStartup(startup);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(BigDecimal.ZERO);
        transactionServices.save(transaction);
        return "redirect:/mentorias";
    }

    @GetMapping("/editarMentoria/{id}")
    public String editarMentoria(@PathVariable Integer id,
                                 Model model) {

        TransactionEntity transaction = transactionServices.findById(id);
        if (transaction == null) {
            return "redirect:/mentorias";
        }

        MentoriaForm mentoriaForm = new MentoriaForm();
        mentoriaForm.setIdMentoria(transaction.getNombreUsu().getIdUsuario());
        mentoriaForm.setStartupId(transaction.getStartup().getId_startup());

        model.addAttribute("mentoriaFormEdit", mentoriaForm);
        model.addAttribute("title", "Editar Mentoria");
        model.addAttribute("mentores", usersServices.findByRol(ROL_MENTOR));
        model.addAttribute("startups", startupServices.findAll());
        model.addAttribute("idMentoria", id);
        return "Usuarios/EditarMentoria";
    }

    @PostMapping("/editarMentoria/{id}")
    public String actualizarMentoria(@PathVariable("id") Integer idTransaccion,
                                     @ModelAttribute MentoriaForm form) {

        TransactionEntity tx = transactionServices.findById(idTransaccion);
        if (tx == null) {
            return "redirect:/mentorias";
        }


        UsersEntity mentor = usersServices.findById(form.getIdMentoria().longValue());
        StartupEntity startup = startupServices.findById(form.getStartupId().intValue());
        tx.setNombreUsu(mentor);
        tx.setStartup(startup);

        transactionServices.save(tx);

        return "redirect:/mentorias";
    }

    @PostMapping("/eliminarMentoria/{id}")
    public String eliminarMentoria(@PathVariable("id") Integer idTransaccion) {
        transactionServices.deleteById(idTransaccion);
        return "redirect:/mentorias";
    }


    @GetMapping(value = "/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("title", "Listar Usuarios");
        List<UsersEntity> lista = usersServices.findAll();
        model.addAttribute("urlCreate", "/createUsuario");
        model.addAttribute("usuarios", lista);
        return "Gusuarios/ListarUsuarios";

    }

    @PostMapping(value = "/eliminarUsuario/")
    public String eliminarUsu(@PathVariable("id") Long idUsuario,
                              RedirectAttributes redirectAttributes) {
        if (idUsuario > 0){
            UsersEntity usuario = usersServices.findById(idUsuario);
            usersServices.findById(idUsuario);
            redirectAttributes.addFlashAttribute("mensaje", "El usuario se encuentra eliminado");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "El usuario no existe");
        }
        return "redirect:/ListarUsuarios";

    }

    @GetMapping(value = "/createUsuario")
    public String crearUsuario(Model model) {
        model.addAttribute("title", "Crear Usuario");
        model.addAttribute("usuario", new UsersEntity());
        model.addAttribute("usuarios", usersServices.findAll());
        model.addAttribute("roles", rolService.findAll());

        return "Gusuarios/CrearUsuario";
    }

    @PostMapping(value = "/crearUsuario")
    public String guardarUsu(@Valid UsersEntity usuario,
                             String imagenAnterior,
                             @RequestParam("archivofoto") MultipartFile foto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("usuario", usuario);
            return "Gusuarios/ListarUsuarios";
        }

        if (foto == null || foto.isEmpty()) {
            System.out.println("No llegó fotito");
        } else {
            System.out.println("Llegó fotito: " + foto.getOriginalFilename());
        }

        String urlImagen = guardarImagen(foto, imagenAnterior);
        if (urlImagen == null || urlImagen.isBlank()) {
            urlImagen = "/images/default-logo.png";
        }

        String pass = new BCryptPasswordEncoder().encode(usuario.getClave());
        usuario.setClave(pass);
        usuario.setFoto(urlImagen);
        usersServices.save(usuario);

        redirectAttributes.addFlashAttribute("mensaje",
                "Usuario guardado correctamente");
        return "redirect:/usuarios";
    }

    private String guardarImagen(MultipartFile imagen, String imagenAnterior) {
        try {

            if (imagen == null || imagen.isEmpty()) {
                return imagenAnterior != null ? imagenAnterior : "/images/default-logo.png";
            }
            BufferedImage original = ImageIO.read(imagen.getInputStream());
            if (original == null) {

                return imagenAnterior;
            }

            BufferedImage thumbnail = Thumbnails.of(original)
                    .size(800, 800)
                    .asBufferedImage();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://api.imgbb.com/1/upload");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                    .addTextBody("key", "15f42e68a3bfe142ea2ea90674d71376", ContentType.TEXT_PLAIN)
                    .addBinaryBody("image", new ByteArrayInputStream(imageBytes),
                            ContentType.DEFAULT_BINARY, imagen.getOriginalFilename());
            httpPost.setEntity(builder.build());

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String resp = EntityUtils.toString(response.getEntity());
                JSONObject data = new JSONObject(resp).getJSONObject("data");
                return data.getString("url");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // En caso de fallo, devolvemos la anterior
        return imagenAnterior;
    }

    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model) {
        UsersEntity usuario = usersServices.findById(id);

        List<RolEntity> roles = rolService.findAll();
        model.addAttribute("title", "Editar Usuario");
        model.addAttribute("usuarioEdit", usuario);
        model.addAttribute("listaRoles", roles);
        model.addAttribute("imagenAnterior", usuario.getFoto());

        return "Gusuarios/EditUsuario";
    }

    @PostMapping(value = "/editarUsuario/{id}")
    public String editarUsu(@ModelAttribute("usuarioEdit") UsersEntity usuarios,
                            @PathVariable("id") Integer idUsuario,
                            RedirectAttributes redirectAttributes,
                            @RequestParam(value = "archivofoto") MultipartFile foto,
                            @RequestParam(value = "imagenAnterior") String imagenAnterior,
                            BindingResult result) throws IOException {
        UsersEntity usersAux = usersServices.findById(idUsuario.longValue());

        usersAux.setEmail(usuarios.getEmail());
        usersAux.setClave(usuarios.getClave());
        usersAux.setCiudad(usuarios.getCiudad());
        usersAux.setApellido_usu(usuarios.getApellido_usu());
        usersAux.setTelefono(usuarios.getTelefono());
        usersAux.setIdRol(usuarios.getIdRol());

        String urlImagen = guardarImagen(foto, imagenAnterior);
        usersAux.setFoto(urlImagen);

        usersServices.save(usersAux);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario editado correctamente");

        return "redirect:/usuarios";


    }

    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (id > 0){
            UsersEntity usuario = usersServices.findById(id);
            usersServices.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje",
                    "Usuario eliminado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("mensaje",
                    "El usuario no existe");
        }

        return "redirect:/usuarios";
    }

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
                                   String imagenAnterior,
                                   @RequestParam("archivofoto") MultipartFile foto,
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

        RolEntity rol = rolServices.findById(usuario.getIdRol().getIdRol().intValue());
        usuario.setIdRol(rol);
        if (foto == null || foto.isEmpty()) {
            System.out.println("No llegó fotito");
        } else {
            System.out.println("Llegó fotito: " + foto.getOriginalFilename());
        }

        String urlImagen = guardarImagen(foto, imagenAnterior);
        if (urlImagen == null || urlImagen.isBlank()) {
            urlImagen = "/images/default-logo.png";
        }

        usuario.setFoto(urlImagen);
        usersServices.save(usuario);
        status.setComplete();
        UsersEntity usuarioExistente = usersServices.findByEmail(usuario.getEmail());
        if (usuarioExistente != null) {
            result.rejectValue("email", "error.usuario", "Este correo ya está registrado.");
            model.addAttribute("title", "Register a new User");
            return "registro/registro";
        }

        redirectAttributes.addFlashAttribute("success", "User registered successfully!");
        return "/registro/login";
    }

    /*-------------------------------------------------------------------------------------------*/

    @GetMapping("/indexMentor")
    public String mostrarInterfazMentor(HttpSession session, Model model) {
        UsersEntity mentor = (UsersEntity) session.getAttribute("mentorActual");
        if(mentor != null){
            System.out.println("Mentor: " + mentor.getNombre_usu());
            model.addAttribute("nombreMentor", mentor.getNombre_usu());
        }else {
            System.out.println("No hay mentor en sesión");
        }
        return "/mentor/indexMentor";
    }

    @GetMapping("/startupsAsignadas")
    public String mostrarStarupsAsignadas(HttpServletRequest request , Model model) {
        // 1) Buscamos todos los usuarios con rol mentor
        UsersEntity mentor = (UsersEntity) request.getSession().getAttribute("mentorActual");
        if (mentor == null) {
            // Si no hay mentor en sesión, redirige al login o lanza error
            return "redirect:/login";
        }

        List<MentoriaDTO> mentorias = new ArrayList<>();


            List<TransactionEntity> txs = transactionServices.findByUsuarioIdUsuario(mentor.getIdUsuario());
            for (TransactionEntity tx : txs) {
                String nombreMentor = mentor.getNombre_usu() + " " + mentor.getApellido_usu();
                String nombreStartup = tx.getStartup().getNombre_startup();

                // Asegúrate de que estas relaciones existen correctamente en StartupEntity
                String nombreEmprendedor = tx.getNombreUsu().getNombre_usu(); // O getNombre(), depende del modelo
                String nombreConvocatoria = tx.getStartup().getConvocatoria().getTitleConvocatoria();
                System.out.println("Nombre convo: " + nombreConvocatoria);
                String logo = tx.getStartup().getLogo();

                mentorias.add(new MentoriaDTO(tx.getIdTransaction(), tx.getStartup().getId_startup(),nombreMentor, nombreStartup, nombreEmprendedor, nombreConvocatoria, logo));
            }

        model.addAttribute("title", "Listado de Mentorías");
        model.addAttribute("mentorias", mentorias);
        return "/mentor/startupsAsignadas";
    }

    @Autowired
    NotificacionService notificacionService;



    @GetMapping("/interfazAdministrador")
    public String mostrarInterfazAdministrador(Model model) {
        RolEntity rol = rolServices.findByNombre("ROLE_ADMINISTRADOR");
        rolServices.save(rol);
        List<NotificacionEntity> notis = notificacionService.obtenerNoLeidas(rol.getIdRol());
        model.addAttribute("notis", notis);
        return "/administrador/interfazAdministrador";
    }

    @GetMapping("/interfazInversor")
    public String mostrarInterfazInversor() {
        return "/inversor/interfazInversor";
    }

}
