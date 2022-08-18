package io.github.zam0k.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.zam0k.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}