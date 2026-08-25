package com.javanauta.aprendendo_spring.controller;

import com.javanauta.aprendendo_spring.business.UsuarioService;
import com.javanauta.aprendendo_spring.controller.dtos.UsuarioDTO;
import com.javanauta.aprendendo_spring.infrastructure.entity.Usuario;
import com.javanauta.aprendendo_spring.infrastructure.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuario));
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Retorna o token JWT. Copie o campo 'token' e cole no Authorize do Swagger.")
    public ResponseEntity<Map<String, String>> login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );

        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "authorization", "Bearer " + token
        ));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Usuario> buscaUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }


    @DeleteMapping("/{email}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable("email") String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }


}
