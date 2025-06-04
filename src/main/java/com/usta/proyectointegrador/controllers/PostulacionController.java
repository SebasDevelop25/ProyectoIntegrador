package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.*;
import com.usta.proyectointegrador.models.dao.NotificacionDAO;
import com.usta.proyectointegrador.models.dao.PostulacionDAO;
import com.usta.proyectointegrador.models.dao.StartupDAO;
import com.usta.proyectointegrador.models.services.*;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class PostulacionController {

    @Autowired
    private PostulacionServices postulacionServices;

    @Autowired
    private ConvocatoriaServices convocatoriaServices;

    @Autowired
    private StartupServices startupServices;


    @Autowired
    private UsersServices usersServices;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private RolServices rolServices;

    @Autowired
    private SeguimientoService seguimientoServices;
    @Autowired
    private PostulacionDAO postulacionDAO;
    @Autowired
    private StartupDAO startupDAO;
    @Autowired
    private NotificacionDAO notificacionDAO;

    @GetMapping("/MisPostulaciones")
    public String listarMisPostulaciones(Model model, Principal principal) {
        String emailUsuario = principal.getName();
        UsersEntity usuario = usersServices.findByEmail(emailUsuario);
        if (usuario == null) {
            return "redirect:/indexEmprendedor";
        }

        List<PostulacionEntity> postulaciones = postulacionServices.findByUsuario(usuario.getIdUsuario());
        model.addAttribute("postulaciones", postulaciones);

        return "emprendedor/ListarPostulaciones";
    }

    @GetMapping(value = "/convocatoriasPostular")
    public String ListarConvoP(Model model) {
        model.addAttribute("title", "Convocatorias");
        model.addAttribute("urlCreate", "/createConvocatoria");
        List<ConvocatoriaEntity> lista = convocatoriaServices.findAll();
        lista.sort(Comparator.comparing(ConvocatoriaEntity::getId_Convocatoria));
        model.addAttribute("convocatorias", lista);
        return "emprendedor/ListarConvoDis";
    }

    @GetMapping(value = "/infoConvocatoria/{id}")
    public String infoConvoP(@PathVariable("id") Long id, Model model) {
        model.addAttribute("title", "Información");
        model.addAttribute("urlCreate", "/createPostulacion");

        ConvocatoriaEntity convocatoria = convocatoriaServices.findById(id);

        if (convocatoria == null) {
            model.addAttribute("error", "Postulación no encontrada");
            return "redirect:/convocatoriasPostular";
        }

        List<PostulacionEntity> listP = postulacionServices.findByConvocatoria(convocatoria);

        model.addAttribute("postulacion", listP);
        model.addAttribute("convocatoria", convocatoria);

        return "emprendedor/infoConvocatoria";
    }


    @GetMapping("/indexEmprendedor")
    public String mostrarInterfazMentor(HttpSession session, Model model) {
        UsersEntity emprendedor = (UsersEntity) session.getAttribute("emprendedorActual");
        if (emprendedor != null) {
            System.out.println("Mentor: " + emprendedor.getNombre_usu());
            model.addAttribute("nombreEmprendedor", emprendedor.getNombre_usu());
        } else {
            System.out.println("No hay emprendedor en sesión");
        }
        return "/emprendedor/interfazEmprendedor";
    }

    @GetMapping(value = "/createPostulacion")
    public String crearPostulacion(@RequestParam("idConvocatoria") Long idConvocatoria, Model model) {
        model.addAttribute("title", "Crear Postulación");
        model.addAttribute("Postulacion", new PostulacionEntity());
        model.addAttribute("convocatorias", convocatoriaServices.findAll());
        model.addAttribute("startups", new StartupEntity());
        model.addAttribute("convocatoria", new ConvocatoriaEntity());
        model.addAttribute("usuarios", new UsersEntity());
        model.addAttribute("idConvocatoria", idConvocatoria);
        return "/emprendedor/postularStartup";
    }

    @PostMapping("/crearPostulacion")
    public String guardarPostulacion(@ModelAttribute("startup") StartupEntity startup, String imagenAnterior,
                                     @RequestParam Long idConvocatoria, @RequestParam(value = "foto") MultipartFile foto,
                                     RedirectAttributes redirectAttributes,
                                     Principal principal) {


        if (foto == null || foto.isEmpty()) {
            System.err.println("No llegó ninguna foto en el request!");
        } else {
            System.out.println("Foto recibida: " + foto.getOriginalFilename()
                    + " tamaño=" + foto.getSize());
        }


        String urlImagen = guardarImagen(foto, imagenAnterior);
        if (urlImagen == null || urlImagen.isBlank()) {
            urlImagen = "/images/default-logo.png";
        }

        // Obtener usuario actual
        UsersEntity usuario = usersServices.findByEmail(principal.getName());

        // Obtener convocatoria
        ConvocatoriaEntity convocatoria = convocatoriaServices.findById(idConvocatoria);

        startup.setLogo(urlImagen);
        startupServices.save(startup);

        // Crear postulación
        PostulacionEntity postulacion = new PostulacionEntity();
        postulacion.setStartup(startup);
        postulacion.setConvocatoria(convocatoria);
        postulacion.setUsuario(usuario);
        postulacion.setFechaPostulacion(LocalDate.now());
        postulacion.setEstado("pendiente");

        postulacionServices.save(postulacion);
        RolEntity rol = rolServices.findByNombre("ROLE_ADMINISTRADOR");
        rolServices.save(rol);

        notificacionService.crearNotificacion(
                "Nueva postulación",
                "La startup " + postulacion.getStartup().getNombre_startup() + " ha enviado una postulación.", rol.getIdRol()
        );


        redirectAttributes.addFlashAttribute("message", "Postulación enviada correctamente.");
        return "redirect:/indexEmprendedor";
    }


    @PostMapping(value = "/eliminarPostulacion/{id}")
    public String eliminarPostulacion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (id != null && id > 0) {
            PostulacionEntity postulacion = postulacionServices.findById(id);
            postulacionServices.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Postulación eliminada");
        } else {
            redirectAttributes.addFlashAttribute("message", "Postulación no encontrada");
        }

        return "redirect:/MisPostulaciones";
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

    public void aprobarStartupDesdeNotificacion(Long notiId) {
        NotificacionEntity noti = notificacionDAO.findById(notiId).orElseThrow();

        String mensaje = noti.getMensaje(); // "La startup FC startup ha enviado una postulación."
        String nombreStartup = mensaje.replace("La startup ", "")
                .replace(" ha enviado una postulación.", "")
                .trim();

        PostulacionEntity postulacion = postulacionDAO.findByStartupNombreExtraido(nombreStartup);
        if (postulacion != null) {
            StartupEntity startup = postulacion.getStartup();
            UsersEntity creador = postulacion.getUsuario(); // Asegúrate que esté seteado en la postulación

            if (creador != null) {
                startup.setUsuario(creador); // Aquí se soluciona tu problema
            } else {
                System.out.println("⚠ La postulación no tiene usuario asociado.");
            }

            postulacion.setEstado("aprobado");
            postulacionDAO.save(postulacion);

            startupDAO.save(startup); // Guardar la startup con el usuario asignado

            convocatoriaServices.registrarStartupEnConvocatoria(startup, postulacion.getConvocatoria());
        } else {
            System.out.println("No se encontró la postulación para: " + noti.getMensaje());
        }

        noti.setLeido(true);
        notificacionDAO.save(noti);
    }


    @PostMapping("/postulaciones/rechazar")
    public String rechazarDesdeNotificacion(@RequestParam Long notificacionId) {
        notificacionService.rechazarStartupDesdeNotificacion(notificacionId);
        return "redirect:/interfazAdministrador";
    }


    @PostMapping("/seguimientos/recibir")
    public String marcarComoRecibido(@RequestParam("idSeguimiento") Integer idSeguimiento, Principal principal) {
        SeguimientoEntity seguimiento = seguimientoServices.findByIds(idSeguimiento);
        if (seguimiento != null && !seguimiento.isRecibido()) {
            seguimiento.setRecibido(true);
            seguimientoServices.save(seguimiento);

            String nombreStartup = seguimiento.getStartup().getNombre_startup();

            // Crear notificación al mentor que dio la mentoría
            notificacionService.crearNotificacionParaUsuario(
                    "Mentoría recibida",
                    "La startup " + nombreStartup + " ha marcado como recibida tu mentoría.",
                    seguimiento.getMentor().getIdRol().getIdRol()
            );
        }
        return "redirect:/seguimientos"; // Redirige a donde desees
    }

    @GetMapping("/seguimientos")
    public String listarMisMentorias(Model model, Principal principal) {
        // 1) Sacamos el email del usuario autenticado
        String emailUsuario = principal.getName();

        // 2) Obtenemos el objeto UsersEntity
        UsersEntity usuario = usersServices.findByEmail(emailUsuario);
        if (usuario == null) {
            // Si no existe usuario, redirige a la página de login o de inicio de emprendedor
            return "redirect:/login"; // (o la ruta que uses para “home emprendedor”)
        }

        Long idEmprendedor = usuario.getIdUsuario();

        // 3) Llamamos al service para traer todas las mentorías de las startups de este emprendedor
        List<SeguimientoEntity> mentorias = seguimientoServices.findByStartupUsuarioId(idEmprendedor);

        // 4) Enviamos la lista al modelo
        model.addAttribute("seguimientos", mentorias);

        // 5) Devolvemos nombre de la plantilla Thymeleaf
        //    (por ejemplo: src/main/resources/templates/emprendedor/ListarMentorias.html)
        return "emprendedor/ListarMentorias";
    }







}


