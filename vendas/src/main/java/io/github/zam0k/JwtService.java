package io.github.zam0k;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.github.zam0k.domain.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${security.jwt.expiracao}")
	private String expiracao;

	private SecretKey key = Keys
			.secretKeyFor(SignatureAlgorithm.HS512);

	public String gerarToken(Usuario usuario) {
		long expString = Long.valueOf(expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now()
				.plusMinutes(expString);

		Instant instant = dataHoraExpiracao
				.atZone(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);
		return Jwts.builder().setSubject(usuario.getUsername())
				.setExpiration(data)
				.signWith(key, SignatureAlgorithm.HS512).compact();

	}

	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication
				.run(VendasApplication.class);
		JwtService service = contexto.getBean(JwtService.class);
		Usuario usuario = Usuario.builder().username("fulano")
				.build();
		String token = service.gerarToken(usuario);
		System.out.println(token);
	}
}
