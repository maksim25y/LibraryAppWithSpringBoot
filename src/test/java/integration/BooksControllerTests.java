package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.mud.Project2Boot.controllers.BooksController;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.services.BooksService;
import ru.mud.Project2Boot.services.PeopleService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class BooksControllerTests {
    private final String BOOKS_URL ="/books/";
    private final String VIEWS_BOOKS = "views/books/";
    @Mock
    private BooksService booksService;
    @Mock
    private PeopleService peopleService;
    @InjectMocks
    private BooksController booksController;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();
    }
    @Test
    public void shouldGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                getDefaultBook(),getDefaultBook()
        );
        when(booksService.findAll(false)).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_URL)
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_BOOKS+"index"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", books));
    }
    @Test
    public void shouldGetBooksNewAndAttributeBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_URL+"new")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_BOOKS+"new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"));
    }
    @Test
    public void shouldHasAttributeBookIfGetBookByIdAndBookExistsAndPersonIdIsNull() throws Exception {
        Book book = getDefaultBook();
        List<Person>people = List.of(new Person());
        when(booksService.findById(book.getId())).thenReturn(Optional.of(book));
        when(peopleService.findAll()).thenReturn(people);
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_URL+"{id}", book.getId())
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_BOOKS+"show"))
                .andExpect(MockMvcResultMatchers.model().attribute("book",book))
                .andExpect(MockMvcResultMatchers.model().attribute("people",people))
                .andExpect(MockMvcResultMatchers.model().attributeExists("give"));
    }
    @Test
    public void shouldHasAttributeBookIfGetBookByIdAndBookExistsAndPersonIsNotNull() throws Exception {
        Book book = getDefaultBook();
        Person person = new Person();
        when(booksService.findById(book.getId())).thenReturn(Optional.of(book));
        when(booksService.getBookPerson(book.getId())).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_URL+"{id}", book.getId())
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_BOOKS+"show"))
                .andExpect(MockMvcResultMatchers.model().attribute("book",book))
                .andExpect(MockMvcResultMatchers.model().attribute("person",person));
    }
    @Test
    public void shouldHasAttributeBookWhenGetEditBook() throws Exception {
        Book book = getDefaultBook();
        when(booksService.findById(book.getId())).thenReturn(Optional.of(book));
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_URL+"{id}/edit", book.getId())
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_BOOKS+"edit"))
                .andExpect(MockMvcResultMatchers.model().attribute("book",book));
    }
    @Test
    public void shouldGetViewSearchWhenGetSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_URL+"search")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_BOOKS+"search"));
    }
    private Book getDefaultBook(){
        Book book = new Book();
        book.setId(2);
        book.setName("Тест");
        book.setAuthor("Тест Тест Тест");
        book.setDate(2000);
        return book;
    }
}
