package com.usta.proyectointegrador.handler;

import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.services.UsersServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UsersServices usersServices;

    public LoginSuccessHandler(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException{

        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equals("ROLE_ADMINISTRADOR")) {
                response.sendRedirect("/interfazAdministrador");
                return;
            } else if (role.getAuthority().equals("ROLE_EMPRENDEDOR")) {

                response.sendRedirect("/user/home");
                return;
            } else if (role.getAuthority().equals("ROLE_INVERSOR")) {
                response.sendRedirect("/interfazInversor");
                return;

            } else if (role.getAuthority().equals("ROLE_MENTOR")) {

                String correo = authentication.getName();
                UsersEntity user = usersServices.findByEmail(correo);

                if(user != null){
                    request.getSession().setAttribute("mentorActual", user);
                    System.out.println("Mentor guardado en la sesion:" + user.getNombre_usu());
                }

                response.sendRedirect("/indexMentor");
                return;
            }
        }
    }
}