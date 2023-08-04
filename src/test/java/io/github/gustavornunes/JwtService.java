package io.github.gustavornunes;

import io.github.gustavornunes.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value(("${security.jwt.expiracao}"))
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String geraToken(Usuario usuario){
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts
                    .builder()
                    .setSubject(usuario.getLogin())
                    .setExpiration(data)
                    .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                    .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJwt(token).getBody();
    }

    public boolean tokenValido(String token){
        try{
            Date dataExpiracao = obterClaims(token).getExpiration();
            LocalDateTime localDateTime = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(localDateTime);


        }catch (Exception e){
            return false;

        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }
}
