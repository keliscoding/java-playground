package io.github.zam0k.api.services;

import io.github.zam0k.api.domain.User;

public interface UserService {
    User findById(Integer id);
}
