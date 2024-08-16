package ru.mud.Project2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.services.BooksService;
import ru.mud.Project2Boot.services.PeopleService;

import java.util.Optional;

@Controller
@Component
@Transactional
@RequestMapping("/books")
public class BooksController {
    private final String VIEWS_BOOKS = "views/books/";
    private final BooksService booksService;
    private final PeopleService peopleService;
    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(name="page",required = false)Integer page,
                         @RequestParam(name="page_per_page",required = false)Integer pagePerPage,
                         @RequestParam(name="sort_by_year",required = false)Boolean sortByYear){
        if(sortByYear==null)sortByYear = false;
        if(page!=null&&pagePerPage!=null){
            model.addAttribute("books",booksService.findWithPagination(page,pagePerPage,sortByYear));
        }else {
            model.addAttribute("books",booksService.findAll(sortByYear));
        }
        return VIEWS_BOOKS+"index";
    }
    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book",new Book());
        return VIEWS_BOOKS+"new";
    }
    @PostMapping
    public String createBook(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())return "views/books/new";
        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String getBook(@PathVariable("id")int id, Model model){
        Optional<Book> bookOptional = booksService.findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            model.addAttribute("book",book);
            if(booksService.getBookPerson(id)==null) {
                model.addAttribute("people", peopleService.findAll());
                model.addAttribute("give", new Person());
            }else {
                model.addAttribute("person", booksService.getBookPerson(id));
                System.out.println(booksService.getBookPerson(id));
            }
        }
        return VIEWS_BOOKS+"show";
    }
    @GetMapping("/{id}/edit")
    public String getEdit(@PathVariable("id")int id,Model model){
        Optional<Book>optionalBook = booksService.findById(id);
        optionalBook.ifPresent(book -> model.addAttribute("book", book));
        return VIEWS_BOOKS+"edit";
    }
    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id")int id,
                             @ModelAttribute("book")@Valid Book book,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return VIEWS_BOOKS+"edit";
        }
        booksService.update(id,book);
        return "redirect:/books";
    }
    @PostMapping("/{id}")
    public String changeBook(@RequestParam(value = "userId",required = false) Integer userId, @PathVariable("id")int id,
                             @ModelAttribute("person") Person person){
        Optional<Book> bookOptional = booksService.findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            if(userId!=null){
                peopleService.deleteBookFromPerson(book);
            }else {
                peopleService.addBookToPeople(person.getId(),book);
            }
        }
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/search")
    public String searchBooks(){
        return VIEWS_BOOKS+"search";
    }
    @PostMapping("/search")
    public String getBooksByName(@RequestParam("search")String search,Model model){
        Book book = booksService.getBooksByPrefix(search);
        if(book!=null){
            model.addAttribute("book",book);
            Person person = booksService.getPersonByBook(book);
            if(person!=null)model.addAttribute("person",person);
        } else{
            model.addAttribute("notFoundBook",true);
        }
        return VIEWS_BOOKS+"search";
    }
}
