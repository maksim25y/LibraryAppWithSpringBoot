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

@Controller
@Component
@Transactional
@RequestMapping("/books")
public class BooksController {
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
        return "views/books/index";
    }
    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book",new Book());
        return "views/books/new";
    }
    @PostMapping
    public String createBook(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String getBook(@PathVariable("id")int id, Model model){
        Book book = booksService.findById(id).get();
        model.addAttribute("book",book);
        if(booksService.getBookPerson(id)==null) {
            model.addAttribute("people", peopleService.findAll());
            model.addAttribute("give", new Person());
        }else {
            model.addAttribute("person", booksService.getBookPerson(id));
            System.out.println(booksService.getBookPerson(id));
        }
        return "views/books/show";
    }
    @GetMapping("/{id}/edit")
    public String getEdit(@PathVariable("id")int id,Model model){
        model.addAttribute("book",booksService.findById(id).get());
        return "views/books/edit";
    }
    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id")int id,
                             @ModelAttribute("book")@Valid Book book,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors())return "views/books/edit";
        booksService.update(id,book);
        return "redirect:/books";
    }
    @PostMapping("/{id}")
    public String changeBook(@RequestParam(value = "userId",required = false) Integer userId, @PathVariable("id")int id,
                             @ModelAttribute("person") Person person){
        Book book = booksService.findById(id).get();
        if(userId!=null){
            peopleService.deleteBookFromPerson(book);
        }else {
            System.out.println(person);
            peopleService.addBookToPeople(person.getId(),book);
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
        return "books/search";
    }
    @PostMapping("/search")
    public String getBooksByName(@RequestParam("search")String search,Model model){
        System.out.println(search);
        Book book = booksService.getBooksByPrefix(search);
        System.out.println(book);
        if(book!=null){
            model.addAttribute("book",book);
            Person person = booksService.getPersonByBook(book);
            if(person!=null)model.addAttribute("person",person);
        }
        else model.addAttribute("notBook",true);
        return "views/books/search";
    }
}
