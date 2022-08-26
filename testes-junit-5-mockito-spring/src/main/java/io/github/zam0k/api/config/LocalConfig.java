package io.github.zam0k.api.config;

import io.github.zam0k.api.domain.User;
import io.github.zam0k.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "Valdir", "valdir@email.com", "123");
        User u2 = new User(null, "Luiz", "luiz@email.com", "123");

        repository.saveAll(List.of(u1, u2));
    }
}
