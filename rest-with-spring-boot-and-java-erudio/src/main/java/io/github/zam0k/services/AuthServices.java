package io.github.zam0k.services;

import io.github.zam0k.data.vo.v1.security.AccountCredentialsVO;
import io.github.zam0k.data.vo.v1.security.TokenVO;
import io.github.zam0k.model.User;
import io.github.zam0k.repositories.UserRepository;
import io.github.zam0k.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthServices {
    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    public ResponseEntity signin(AccountCredentialsVO data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            User user = repository.findByUsername(username);

            TokenVO tokenResponse;

            if(user == null) {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }

            tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw  new BadCredentialsException("Invalid username/password supplied!");
        }
    }
}
