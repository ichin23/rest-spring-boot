package br.com.ichin23.rest_spring_boot.repository;

import br.com.ichin23.rest_spring_boot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    //a
}
