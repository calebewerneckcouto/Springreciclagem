package com.javanauta.aprendendo_spring.business;

import com.javanauta.aprendendo_spring.infrastructure.entity.Usuario;
import com.javanauta.aprendendo_spring.infrastructure.exceptions.ConflictException;
import com.javanauta.aprendendo_spring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario salvaUsuario(Usuario usuario) {
        try {

            emailExiste(usuario.getEmail());
            return usuarioRepository.save(usuario);

        } catch (ConflictException e) {
            throw new ConflictException("Email ja cadastrado",e.getCause());
        }
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email ja cadastrado" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email ja cadstrado", e.getCause());

        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
