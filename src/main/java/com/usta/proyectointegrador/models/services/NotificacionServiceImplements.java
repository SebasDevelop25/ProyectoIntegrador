package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.*;
import com.usta.proyectointegrador.models.dao.NotificacionDAO;
import com.usta.proyectointegrador.models.dao.PostulacionDAO;
import com.usta.proyectointegrador.models.dao.RolDAO;
import com.usta.proyectointegrador.models.dao.StartupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Autowired
    private StartupDAO startupDAO;

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

    @Override
    public void crearNotificacionParaUsuario(String titulo, String mensaje, Long idRol) {

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

