package com.usta.proyectointegrador.handler;

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
                response.sendRedirect("/indexMentor");
                return;
            }
        }
    }
}