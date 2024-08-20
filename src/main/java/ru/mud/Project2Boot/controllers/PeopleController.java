package ru.mud.Project2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.services.PeopleService;
import ru.mud.Project2Boot.util.PersonValidator;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final String VIEWS_PEOPLE = "views/people/";
    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getPeople(Model model){
        model.addAttribute("people",peopleService.findAll());
        return VIEWS_PEOPLE+"index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id")Integer id, Model model){
        Optional<Person>personOptional = peopleService.findById(id);
        if(personOptional.isPresent()){
            model.addAttribute("person",personOptional.get());
            List<Book> books = peopleService.getBooksByPersonId(id);
            if(!books.isEmpty())
                model.addAttribute("books",books);
        }
        return VIEWS_PEOPLE+"show";
    }

    @GetMapping("/new")
    public String addGet(Model model){
        model.addAttribute("person",new Person());
        return VIEWS_PEOPLE+"new";
    }
    @PostMapping
    public String addNew(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())return "views/people/new";
        peopleService.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String editGet(@PathVariable("id")Integer id,Model model){
        Optional<Person>personOptional = peopleService.findById(id);
        personOptional.ifPresent(person->model.addAttribute("person",person));
        return VIEWS_PEOPLE+"edit";
    }
    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,@PathVariable("id") Integer id){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())return VIEWS_PEOPLE+"edit";
        peopleService.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Integer id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}