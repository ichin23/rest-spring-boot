package br.com.ichin23.rest_spring_boot.services;

import br.com.ichin23.rest_spring_boot.data.dto.PersonDTO;
import br.com.ichin23.rest_spring_boot.exception.ResourceNotFoundException;
import static br.com.ichin23.rest_spring_boot.mapper.ObjectMapper.parseListObjects;
import static br.com.ichin23.rest_spring_boot.mapper.ObjectMapper.parseObject;

import br.com.ichin23.rest_spring_boot.model.Person;
import br.com.ichin23.rest_spring_boot.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServices {
    //a
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll(){

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id){
        logger.info("Finding 1 person");

        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating one person!");
        var entity = parseObject(person, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one person!");
        Person entity = repository.findById(person.getId()).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }
    public void delete(Long id){
        logger.info("Deleting one person!");

        Person entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }


}
