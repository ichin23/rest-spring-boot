package br.com.ichin23.rest_spring_boot.services;

import br.com.ichin23.rest_spring_boot.controllers.BooksController;
import br.com.ichin23.rest_spring_boot.data.dto.BookDTO;
import static br.com.ichin23.rest_spring_boot.mapper.ObjectMapper.parseListObjects;
import static br.com.ichin23.rest_spring_boot.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.ichin23.rest_spring_boot.data.dto.PersonDTO;
import br.com.ichin23.rest_spring_boot.exception.RequiredObjectIsNullException;
import br.com.ichin23.rest_spring_boot.exception.ResourceNotFoundException;
import br.com.ichin23.rest_spring_boot.model.Book;
import br.com.ichin23.rest_spring_boot.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BooksRepository repository;

    public List<BookDTO> findAll(){
        var books = parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(this::hateoasLinks);
        return books;
    }

    public BookDTO findById(Long id){
        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        var book = parseObject(entity, BookDTO.class);
        hateoasLinks(book);
        return book;
    }

    public BookDTO create(BookDTO book){

        if (book==null) throw new RequiredObjectIsNullException();

        var entity = parseObject(book, Book.class);
        var dto = parseObject(repository.save(entity), BookDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public BookDTO update(BookDTO book){

        if (book==null) throw new RequiredObjectIsNullException();

        var entity = repository.findById(book.getId()).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunch_date(book.getLaunch_date());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var dto = parseObject(repository.save(entity), BookDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }

    private void hateoasLinks(BookDTO book){
        book.add(linkTo(methodOn(BooksController.class).findByID(book.getId())).withSelfRel().withType("GET"));
        book.add(linkTo(methodOn(BooksController.class).findAll()).withRel("findAll").withType("GET"));
        book.add(linkTo(methodOn(BooksController.class).create(book)).withRel("create").withType("POST"));
        book.add(linkTo(methodOn(BooksController.class).update(book)).withRel("update").withType("PUT"));
        book.add(linkTo(methodOn(BooksController.class).delete(book.getId())).withRel("delete").withType("DELETE"));
    }
}
