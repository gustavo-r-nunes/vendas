package io.github.gustavornunes.service.iml;

import io.github.gustavornunes.exception.SenhaInvalidaException;
import io.github.gustavornunes.model.Usuario;
import io.github.gustavornunes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public UserDetails autenticar(Usuario usuario){
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean matches = encoder.matches(usuario.getSenha(), userDetails.getPassword());
        if(matches){
            return userDetails;
        }

        throw new SenhaInvalidaException();
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(s).orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));
        String[] roles = usuario.isAdmin()? new String[]{"ADMIN", "USER"}: new String[] {"USER"};

        return User.builder().username(usuario.getLogin()).password(usuario.getSenha()).roles(roles).build();
    }

    @Transactional
    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }
}
