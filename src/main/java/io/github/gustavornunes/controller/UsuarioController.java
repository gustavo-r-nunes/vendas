package io.github.gustavornunes.controller;

import io.github.gustavornunes.dto.CredenciaisDTO;
import io.github.gustavornunes.dto.TokenDTO;
import io.github.gustavornunes.exception.SenhaInvalidaException;
import io.github.gustavornunes.model.Usuario;
import io.github.gustavornunes.security.jwt.JwtService;
import io.github.gustavornunes.service.iml.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sun.security.util.Password;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioServiceImpl service;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return service.salvar(usuario);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario build = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = service.autenticar(build);
            String token = jwtService.gerarToken(build);
            return new TokenDTO(build.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


}
