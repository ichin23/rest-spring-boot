package br.com.ichin23.rest_spring_boot.repository;

import br.com.ichin23.rest_spring_boot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
