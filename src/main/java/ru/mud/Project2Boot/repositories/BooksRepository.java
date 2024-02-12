package ru.mud.Project2Boot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.mud.Project2Boot.models.Book;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book,Integer> {
    void deleteBookById(int id);
    Book findByNameStartingWith(String search);
    List<Book> findByOrderByDate();
}
