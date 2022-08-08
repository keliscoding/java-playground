package io.github.zam0k.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.zam0k.service.implementation.UsuarioService;

public class JwtAuthFilter extends OncePerRequestFilter {

	private JwtService jwtService;

	private UsuarioService usuarioService;

	public JwtAuthFilter(JwtService jwtService, UsuarioService usuarioService) {
		super();
		this.jwtService = jwtService;
		this.usuarioService = usuarioService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");

		if (authorization != null && authorization.startsWith("Bearer")) {
			String token = authorization.split(" ")[1];
			boolean isValid = jwtService.tokenValido(token);

			if (isValid) {

				String usernameUsuario = jwtService.obterUsernameUsuario(token);
				UserDetails usuario = usuarioService
						.loadUserByUsername(usernameUsuario);
				UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
						usuario, null, usuario.getAuthorities());
				user.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(user);
			}
		}

		filterChain.doFilter(request, response);

	}

}
