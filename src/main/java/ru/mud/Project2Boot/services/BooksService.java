package ru.mud.Project2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.repositories.BooksRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public List<Book> findAll(boolean sortByDate){
        if(sortByDate)return booksRepository.findByOrderByDate();
        return booksRepository.findAll();
    }
    public Optional<Book> findById(int id){
        return booksRepository.findById(id);
    }
    public void save(Book book) {
        booksRepository.save(book);
    }
    public void update(int id,Book book) {
        book.setId(id);
        booksRepository.save(book);
    }
    public void delete(int id){
        booksRepository.deleteBookById(id);
    }
    public Person getBookPerson(int id) {
        Optional<Book>bookOptional = booksRepository.findById(id);
        return bookOptional.map(Book::getPerson).orElse(null);
    }
    public Book getBooksByPrefix(String search) {
        return booksRepository.findByNameStartingWith(search);
    }

    public Person getPersonByBook(Book book) {
        return book.getPerson();
    }

    public List<Book> findWithPagination(Integer page, Integer pagePerPage,boolean sortByDate) {
        List<Book>books = findAll(sortByDate);
        List<Book>result = new ArrayList<>();
        int pos=page*pagePerPage-1;
        while (pos<books.size()&&pagePerPage!=0){
            result.add(books.get(pos));
            pos++;
            pagePerPage--;
        }
        return result;
    }
}
