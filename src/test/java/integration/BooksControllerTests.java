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
import ru.mud.Project2Boot.services.BooksService;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class BooksControllerTests {
    @Mock
    private BooksService booksService;
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
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("views/books/index"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", books));
    }
    private Book getDefaultBook(){
        return new Book();
    }
}
