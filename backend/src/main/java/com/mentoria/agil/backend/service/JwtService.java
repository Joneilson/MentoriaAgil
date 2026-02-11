package com.mentoria.agil.backend.service;

import com.mentoria.agil.backend.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // Em produção, usar uma chave longa e secreta vinda de variáveis de ambiente
    private final String SECRET_KEY = "sua_chave_secreta_muito_longa_e_segura_para_o_projeto";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(getSigningKey())
                .compact();
    }
}