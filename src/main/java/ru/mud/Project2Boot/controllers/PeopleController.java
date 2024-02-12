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

@Controller
@RequestMapping("/people")
public class PeopleController {
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
        return "views/people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){
        model.addAttribute("person",peopleService.findById(id).get());
        List<Book> books = peopleService.getBooksByPersonId(id);
        if(!books.isEmpty())
            model.addAttribute("books",books);
        else
            model.addAttribute("notBooks",true);
        return "views/people/show";
    }

    @GetMapping("/new")
    public String addGet(Model model){
        model.addAttribute("person",new Person());
        return "views/people/new";
    }
    @PostMapping
    public String addNew(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, Model model){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())return "views/people/new";
        peopleService.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String editGet(@PathVariable("id")int id,Model model){
        model.addAttribute("person",peopleService.findById(id).get());
        return "views/people/edit";
    }
    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,@PathVariable("id") int id){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())return "people/edit";
        peopleService.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        System.out.println(id);
        peopleService.delete(id);
        return "redirect:/people";
    }
}