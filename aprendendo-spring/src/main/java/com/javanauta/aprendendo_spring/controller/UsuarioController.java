package com.javanauta.aprendendo_spring.controller;

import com.javanauta.aprendendo_spring.business.UsuarioService;
import com.javanauta.aprendendo_spring.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> salvaUsuario( @RequestBody Usuario usuario) {
       return ResponseEntity.ok(usuarioService.salvaUsuario(usuario));
    }


}
