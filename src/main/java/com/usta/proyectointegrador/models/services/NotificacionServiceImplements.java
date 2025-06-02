package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.NotificacionEntity;
import com.usta.proyectointegrador.entities.PostulacionEntity;
import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.models.dao.NotificacionDAO;
import com.usta.proyectointegrador.models.dao.PostulacionDAO;
import com.usta.proyectointegrador.models.dao.RolDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceImplements implements NotificacionService {

    @Autowired
    RolDAO rolDao;

    @Autowired
    NotificacionDAO notificacionDAO;
    @Autowired
    private PostulacionDAO postulacionDAO;
    @Autowired
    private ConvocatoriaServices convocatoriaServices;

    @Override
    public void crearNotificacion(String titulo, String mensaje, Long rolDestino) {
        NotificacionEntity notificacion = new NotificacionEntity();
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);


        RolEntity rol = rolDao.findById(rolDestino)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + rolDestino));

        notificacion.setRol(rol); // Asumiendo que existe un campo 'rol' en la entidad
        System.out.println("⏺ Guardando notificación para el rol: " + rolDestino);
        notificacionDAO.save(notificacion);
    }

    @Override
    public List<NotificacionEntity> obtenerNoLeidas(Long idNotificacion) {
        RolEntity rol = rolDao.findById(idNotificacion)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el rol con ID: " + idNotificacion));

        return notificacionDAO.findByRolAndLeidoFalse(rol);
    }

    @Override
    public void marcarLeida(Long idNotificacion) {
        NotificacionEntity notificacion = notificacionDAO.findById(idNotificacion)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la notificación con ID: " + idNotificacion));

        notificacion.setLeido(true);
        notificacionDAO.save(notificacion);
    }

    @Override
    public void aprobarStartupDesdeNotificacion(Long notiId) {
        NotificacionEntity noti = notificacionDAO.findById(notiId).orElseThrow();

        String mensaje = noti.getMensaje(); // "La startup FC startup ha enviado una postulación."
        String nombreStartup = mensaje.replace("La startup ", "")
                .replace(" ha enviado una postulación.", "")
                .trim();


        PostulacionEntity postulacion = postulacionDAO.findByStartupNombreExtraido(nombreStartup);
        if (postulacion != null) {
            System.out.println("Startup: " + postulacion.getStartup().getNombre_startup());
            System.out.println("Convocatoria: " + (postulacion.getConvocatoria() != null ? postulacion.getConvocatoria().getTitleConvocatoria() : "null"));
            postulacion.setEstado("aprobado");
            postulacionDAO.save(postulacion);
            convocatoriaServices.registrarStartupEnConvocatoria(postulacion.getStartup(), postulacion.getConvocatoria());
        } else {
            System.out.println("No se encontró la postulación para: " + noti.getMensaje());
            // Opcional: lanzar una excepción personalizada o mostrar mensaje al usuario
        }

        noti.setLeido(true);
        notificacionDAO.save(noti);

        // Registrar la startup en la convocatoria automáticamente, si aplica
        convocatoriaServices.registrarStartupEnConvocatoria(postulacion.getStartup(), postulacion.getConvocatoria());
    }

    @Override
    public void rechazarStartupDesdeNotificacion(Long notiId) {
        NotificacionEntity noti = notificacionDAO.findById(notiId).orElseThrow();


        String mensaje = noti.getMensaje(); // "La startup FC startup ha enviado una postulación."
        String nombreStartup = mensaje.replace("La startup ", "")
                .replace(" ha enviado una postulación.", "")
                .trim();


        PostulacionEntity postulacion = postulacionDAO.findByStartupNombreExtraido(nombreStartup);
        postulacion.setEstado("rechazada");
        postulacionDAO.save(postulacion);
        noti.setLeido(true);
        notificacionDAO.save(noti);
    }


}

