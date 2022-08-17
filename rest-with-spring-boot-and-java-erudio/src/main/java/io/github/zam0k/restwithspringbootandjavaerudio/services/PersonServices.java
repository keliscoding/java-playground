package io.github.zam0k.restwithspringbootandjavaerudio.services;

import io.github.zam0k.restwithspringbootandjavaerudio.data.vo.v1.PersonVO;
import io.github.zam0k.restwithspringbootandjavaerudio.exceptions.ResourceNotFoundException;
import io.github.zam0k.restwithspringbootandjavaerudio.model.Person;
import io.github.zam0k.restwithspringbootandjavaerudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repository;

    public PersonVO findById(Long id) {
        logger.info("Finding one person...");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
    }

    public List<PersonVO> findAll() {
        logger.info("Finding all people...");
        return repository.findAll();
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person...");


        return repository.save(person);
    }

    public PersonVO update(PersonVO person, Long id) {
        logger.info("Updating one person...");
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one person...");
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        repository.delete(entity);
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("Some address in Brasil " + i);
        person.setGender("Male");
        return person;
    }

}
