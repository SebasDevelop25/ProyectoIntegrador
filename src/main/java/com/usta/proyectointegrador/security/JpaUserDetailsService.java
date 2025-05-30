package com.usta.proyectointegrador.security;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersDAO usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity usuario = usuarioDao.findByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("The user doesn't exist");
        }

        System.out.println("Checking role...: " + usuario.getIdRol().getRol());

        return new User(
                usuario.getEmail(),
                usuario.getClave(),
                mapearAutoridadesRol(usuario.getIdRol())
        );
    }

    private Collection<? extends GrantedAuthority>
    mapearAutoridadesRol(RolEntity rol) {
        return List.of(new SimpleGrantedAuthority(rol.getRol()));
    }

}