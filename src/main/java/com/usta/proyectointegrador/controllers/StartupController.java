package com.usta.proyectointegrador.controllers;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.StartupEntity;
import com.usta.proyectointegrador.models.services.ConvocatoriaServices;
import com.usta.proyectointegrador.models.services.StartupServices;
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
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
public class StartupController {
    @Autowired
    StartupServices startupServices;

    @Autowired
    ConvocatoriaServices convocatoriaServices;

    @GetMapping(value = "/Startups/{id}")
    public String ListarStar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("title", "Startups de la convocatoria");
        model.addAttribute("urlCreate", "/createStartup");
        ConvocatoriaEntity convocatoria = convocatoriaServices.findById(id);
        if (convocatoria == null) {
            model.addAttribute("error", "Convocatoria no encontrada");
            return "redirect:/convocatorias";
        }
        List<StartupEntity> listaStar = startupServices.findByConvocatoria(convocatoria);
        model.addAttribute("Startups", listaStar);
        return "/Startups/ListarStartup";
    }

    /*---------------------------------------------------*/
    @PostMapping(value = "/eliminarStartup/{id}")
    public String eliminarStartup(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (id > 0) {
            StartupEntity startup = startupServices.findById(id);
            startupServices.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Startup eliminada");

        } else {
            redirectAttributes.addFlashAttribute("message", "Startup no encontrada");
        }
        return "redirect:/convocatorias" ;
    }

    @GetMapping(value = "/createStartup")
    public String createStartup(Model model) {
        model.addAttribute("title", "Crear Startup");
        model.addAttribute("Startup", new StartupEntity());
        model.addAttribute("convocatorias", convocatoriaServices.findAll());
        List<ConvocatoriaEntity> convocatorias = convocatoriaServices.findAll();
        model.addAttribute("convocatorias", convocatorias);
        return "/Startups/CreateStartup";
    }

    @PostMapping(value = "/crearStartup")
    public String guardarStar(@Valid StartupEntity startup,
                              String imagenAnterior,
                              @RequestParam(value = "foto") MultipartFile foto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.Startup", result);
            redirectAttributes.addFlashAttribute("Startup", startup);
            return "redirect:/createStartup";
        }

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

        startup.setLogo(urlImagen);
        startupServices.save(startup);
        Long idConv = startup.getConvocatoria().getId_Convocatoria();

        redirectAttributes.addFlashAttribute("message", "Startup creada");
        return "redirect:/Startups/" + idConv;
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


    @GetMapping("/editarStartup/{id}")
    public String editarStartup(@PathVariable("id") Integer idStartup, Model model) {
        StartupEntity startup = startupServices.findById(idStartup);
        if (startup == null) {
            return "redirect:/convocatorias";
        }
        model.addAttribute("title", "Editar Startup");
        model.addAttribute("startupEdit", startup);
        model.addAttribute("imagenAnterior", startup.getLogo());

        // <-- Aquí añades la lista de convocatorias
        List<ConvocatoriaEntity> convocatorias = convocatoriaServices.findAll();
        model.addAttribute("convocatorias", convocatorias);

        return "Startups/editStartup";
    }


    @PostMapping(value = "/editarStartup/{id}")
    public String editStartup(@ModelAttribute("startupEdit") StartupEntity startup,
                              @PathVariable("id") Integer idStartup,
                              @RequestParam("convocatoriaId") Integer convocatoriaId,
                              RedirectAttributes redirectAttributes,
                              @RequestParam(value = "foto") MultipartFile foto,
                              @RequestParam(value = "imagenAnterior") String imagenAnterior,
                              BindingResult result) throws IOException {
        StartupEntity startupAux = startupServices.findById(idStartup);
        startupAux.setId_startup(idStartup);
        startupAux.setNombre_startup(startup.getNombre_startup());
        startupAux.setSector(startup.getSector());
        startupAux.setDescripción(startup.getDescripción());

        ConvocatoriaEntity conv = convocatoriaServices.findById(convocatoriaId.longValue());
        startupAux.setConvocatoria(conv);

        String urlImagen = guardarImagen(foto, imagenAnterior);
        startupAux.setLogo((urlImagen == null || urlImagen.isBlank())
                ? imagenAnterior
                : urlImagen);


        startupServices.actualizarStar(startupAux);
        redirectAttributes.addFlashAttribute("message", "Startup editada");
        return "redirect:/convocatorias";

    }

    @GetMapping(value = "/startups")
    public String ListarStartups(Model model) {
        model.addAttribute("title", "Startups");
        model.addAttribute("urlCreate", "/VerStartups");
        List<StartupEntity> lista = startupServices.findAll();
        lista.sort(Comparator.comparing(StartupEntity::getId_startup));
        model.addAttribute("startups", lista);
        return "Startups/VerStartups";
    }

    @GetMapping(value = "/infoStartups")
    public String VisualizarStartups(Model model) {
        model.addAttribute("title", "Startups");
        model.addAttribute("urlCreate", "/informacionStartups");
        List<StartupEntity> lista = startupServices.findAll();
        lista.sort(Comparator.comparing(StartupEntity::getId_startup));
        model.addAttribute("startups", lista);
        return "Startups/informacionStartups";
    }

    @GetMapping(value = "/invertirStartup")
    public String InvertirStartups(Model model) {
        model.addAttribute("title", "Startups");
        model.addAttribute("urlCreate", "/invertirStartups");
        List<StartupEntity> lista = startupServices.findAll();
        lista.sort(Comparator.comparing(StartupEntity::getId_startup));
        model.addAttribute("startups", lista);
        return "Startups/invertirStartups";
    }

    @GetMapping(value = "/inversiones")
    public String Inversiones(Model model) {
        model.addAttribute("title", "Inversiones");
        model.addAttribute("urlCreate", "/inversionesInversor");
        List<StartupEntity> lista = startupServices.findAll();
        lista.sort(Comparator.comparing(StartupEntity::getId_startup));
        model.addAttribute("startups", lista);
        return "inversor/inversionesInversor";
    }

}





