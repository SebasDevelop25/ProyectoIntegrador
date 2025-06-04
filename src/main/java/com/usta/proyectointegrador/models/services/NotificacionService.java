package com.usta.proyectointegrador.models.services;
import com.usta.proyectointegrador.entities.NotificacionEntity;
import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.entities.UsersEntity;

import java.util.List;

public interface NotificacionService {

    public void crearNotificacion(String titulo, String mensaje, Long idRol);
    public List<NotificacionEntity> obtenerNoLeidas(Long idRol);
    public void marcarLeida(Long idNotificacion);
    public void rechazarStartupDesdeNotificacion(Long notiId);
    public void aprobarStartupDesdeNotificacion(Long notiId);
    public void crearNotificacionParaUsuario(String titulo, String mensaje, Long idRol);



}
