package io.github.zam0k.api.services.impl;

import io.github.zam0k.api.domain.User;
import io.github.zam0k.api.repositories.UserRepository;
import io.github.zam0k.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }
}
