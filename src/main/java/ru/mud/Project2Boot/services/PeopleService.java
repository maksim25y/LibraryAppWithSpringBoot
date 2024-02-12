package ru.mud.Project2Boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.repositories.PeopleRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    public Optional<Person> findById(Integer id){return peopleRepository.findById(id);}
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id,Person person){
        person.setId(id);
        peopleRepository.save(person);
    }
    @Transactional
    public List<Book>getBooksByPersonId(int id){
        Optional<Person>person = peopleRepository.findById(id);
        Hibernate.initialize(person.get().getBookList());
        List<Book>books = person.get().getBookList();
        Date now = new Date();
        for(Book book:books){
            Date bookTaken = book.getTaken();
            long curr = now.getTime()-bookTaken.getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(curr);
            book.setBad(diffInDays>10);
        }
        return books;
    }
    @Transactional
    public void deleteBookFromPerson(Book book) {
        Person person = book.getPerson();
        book.setPerson(null);
        Hibernate.initialize(person.getBookList().remove(book));
        book.setTaken(null);
        System.out.println("Good");
    }

    @Transactional
    public void addBookToPeople(int id, Book book) {
        Person person = peopleRepository.findById(id).get();
        person.getBookList().add(book);
        book.setTaken(new Date());
        book.setPerson(person);
    }
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
    public Optional<Person>getPersonByInfo(String name){
        return peopleRepository.getPersonByInfo(name);
    }
}