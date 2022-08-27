package io.github.zam0k.api.services.impl;

import io.github.zam0k.api.domain.User;
import io.github.zam0k.api.domain.dto.UserDTO;
import io.github.zam0k.api.repositories.UserRepository;
import io.github.zam0k.api.services.UserService;
import io.github.zam0k.api.services.exceptions.DataIntegrityViolationException;
import io.github.zam0k.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User create(UserDTO dto) {
        findByEmail(dto);
        User user = mapper.map(dto, User.class);
        return repository.save(user);
    }

    @Override
    public User update(UserDTO dto) {
        findByEmail(dto);
        return repository.save(mapper.map(dto, User.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(UserDTO dto) {
        Optional<User> user = repository.findByEmail(dto.getEmail());
        if(user.isPresent() && !user.get().getId().equals(dto.getId()))
            throw new DataIntegrityViolationException("Email já cadastrado");
    }
}
