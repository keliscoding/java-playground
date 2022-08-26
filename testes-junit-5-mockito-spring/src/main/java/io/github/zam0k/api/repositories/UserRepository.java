package io.github.zam0k.api.repositories;

import io.github.zam0k.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
