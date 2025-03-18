package br.com.ichin23.rest_spring_boot.unittests.mapper.mocks;

import br.com.ichin23.rest_spring_boot.data.dto.BookDTO;
import br.com.ichin23.rest_spring_boot.data.dto.PersonDTO;
import br.com.ichin23.rest_spring_boot.model.Book;
import br.com.ichin23.rest_spring_boot.model.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setLaunch_date(new Date());
        book.setTitle("Title Test" + number);
        book.setId(number.longValue());
        book.setPrice(9.99);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setAuthor("Author Test" + number);
        book.setLaunch_date(new Date());
        book.setTitle("Title Test" + number);
        book.setId(number.longValue());
        book.setPrice(9.99);
        return book;
    }

}