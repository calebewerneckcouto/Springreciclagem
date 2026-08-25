package com.javanauta.aprendendo_spring.business;

import com.javanauta.aprendendo_spring.infrastructure.entity.Usuario;
import com.javanauta.aprendendo_spring.infrastructure.exceptions.ConflictException;
import com.javanauta.aprendendo_spring.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.aprendendo_spring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvaUsuario(Usuario usuario) {
        try {
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);

        } catch (ConflictException e) {
            throw new ConflictException("Email ja cadastrado", e.getCause());
        }
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email ja cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email ja cadstrado", e.getCause());

        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email nao encontrado" + email));


    }


    @Transactional
    public void deletaUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email nao encontrado" + email));
        usuarioRepository.delete(usuario);
    }
}


