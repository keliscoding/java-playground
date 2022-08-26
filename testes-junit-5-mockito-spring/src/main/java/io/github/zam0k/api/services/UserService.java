package io.github.zam0k.api.services;

import io.github.zam0k.api.domain.User;
import io.github.zam0k.api.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO dto);
    User update(UserDTO dto);
}
