package io.github.zam0k.services;

import io.github.zam0k.model.User;
import io.github.zam0k.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name " + username + "!");
        User user = repository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return user;
    }
}
