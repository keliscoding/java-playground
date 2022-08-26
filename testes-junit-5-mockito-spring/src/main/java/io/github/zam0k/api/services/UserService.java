package io.github.zam0k.api.services;

import io.github.zam0k.api.domain.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);

    List<User> findAll();
}
