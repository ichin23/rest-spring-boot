package br.com.ichin23.rest_spring_boot.services;

import br.com.ichin23.rest_spring_boot.controllers.PersonController;
import br.com.ichin23.rest_spring_boot.data.dto.PersonDTO;
import br.com.ichin23.rest_spring_boot.exception.RequiredObjectIsNullException;
import br.com.ichin23.rest_spring_boot.exception.ResourceNotFoundException;
import static br.com.ichin23.rest_spring_boot.mapper.ObjectMapper.parseListObjects;
import static br.com.ichin23.rest_spring_boot.mapper.ObjectMapper.parseObject;

import br.com.ichin23.rest_spring_boot.model.Person;
import br.com.ichin23.rest_spring_boot.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServices {
    //a
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll(){

        var persons =  parseListObjects(repository.findAll(), PersonDTO.class);

        persons.forEach( this::addHateoasLinks);

        return persons;
    }

    public PersonDTO findById(Long id){
        logger.info("Finding 1 person");

        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        var dto = parseObject(entity, PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO person){

        if (person==null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person!");
        var entity = parseObject(person, Person.class);
        var dto =  parseObject(repository.save(entity), PersonDTO.class);

        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person){

        if (person==null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person!");
        Person entity = repository.findById(person.getId()).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }
    public void delete(Long id){
        logger.info("Deleting one person!");

        Person entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private void addHateoasLinks( PersonDTO dto){
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));

    }
}
