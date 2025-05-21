package com.proyecto.proyecto.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.bean.LogindataBean;
import com.proyecto.proyecto.entity.usuarioEntity;
import com.proyecto.proyecto.exception.UnauthorizedAccessException;
import com.proyecto.proyecto.repository.usuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Autowired
    JWTService JWTHelper;

    @Autowired
    usuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

     public boolean checkLogin(LogindataBean oLogindataBean) {
        if (oUsuarioRepository.findByEmailAndPassword(oLogindataBean.getEmail(), oLogindataBean.getPassword())
                .isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private Map<String, String> getClaims(String email) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", email);
        return claims;
    };

     public String getToken(String email) {
        return JWTHelper.generateToken(getClaims(email));
    }

    public usuarioEntity getUsuarioFromToken() {
        if (oHttpServletRequest.getAttribute("email") == null) {
            throw new UnauthorizedAccessException("No hay usuario en la sesión");
        } else {
            String email = oHttpServletRequest.getAttribute("email").toString();
            return oUsuarioRepository.findByEmail(email).get();
        }                
    }

    public boolean isSessionActive() {
        return oHttpServletRequest.getAttribute("email") != null;
    }

    public boolean isAdmin() {
        return this.getUsuarioFromToken().getTipousuario().getId() == 1L;
    }

    public boolean isUsuario() {
        return this.getUsuarioFromToken().getTipousuario().getId() == 2L;
    }

    public boolean isAdminOrUsuario() {
        return this.isAdmin() || this.isUsuario();
    }

    public boolean isUsuarioWithItsOwnData(Long id) {
        usuarioEntity oUsuarioEntity = this.getUsuarioFromToken();
        return this.isUsuario() && oUsuarioEntity.getId() == id;
    }


    
}
