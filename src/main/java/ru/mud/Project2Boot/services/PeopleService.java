package ru.mud.Project2Boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.repositories.PeopleRepository;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
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
    public void save(Person person){
        peopleRepository.save(person);
    }
    public void update(Integer id,Person person){
        person.setId(id);
        peopleRepository.save(person);
    }
    public List<Book>getBooksByPersonId(Integer id){
        Optional<Person> personOptional = peopleRepository.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            Hibernate.initialize(person.getBookList());
            return person.getBookList().stream()
                    .peek(book -> {
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(new Date().getTime() - book.getTaken().getTime());
                        book.setBad(diffInDays > 10);
                    })
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public void deleteBookFromPerson(Book book) {
        if(book!=null){
            Person person = book.getPerson();
            book.setPerson(null);
            Hibernate.initialize(person.getBookList().remove(book));
            book.setTaken(null);
        }
    }
    public void addBookToPeople(Integer id, Book book) {
        Optional<Person>personOptional = peopleRepository.findById(id);
        if(personOptional.isPresent()){
            Person person = personOptional.get();
            if(book!=null){
                person.getBookList().add(book);
                book.setTaken(new Date());
                book.setPerson(person);
            }
        }
    }
    public void delete(Integer id) {
        peopleRepository.deleteById(id);
    }
    public Optional<Person>getPersonByInfo(String name){
        return peopleRepository.getPersonByInfo(name);
    }
}