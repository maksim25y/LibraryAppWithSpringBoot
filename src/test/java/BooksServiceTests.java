import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mud.Project2Boot.Project2BootApplication;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.repositories.BooksRepository;
import ru.mud.Project2Boot.services.BooksService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Project2BootApplication.class)
public class BooksServiceTests {
    @Autowired
    private BooksService booksService;
    @Autowired
    private BooksRepository booksRepository;
    @BeforeEach
    public void cleanAllTable(){
        booksRepository.deleteAll();
    }
    @Test
    public void whenInsertBookThenItIsPresentInDatabase(){
        Book book = new Book();
        book.setId(1);
        book.setName("Тест");
        book.setDate(1990);
        book.setAuthor("Тест Тест Тест");
        booksService.save(book);
        Optional<Book>savedBook = booksRepository.findById(book.getId());
        assertTrue(savedBook.isPresent());
    }
    @Test
    public void whenInsertBookThenSizeOfAllBooksEquals1(){
        Book book = new Book();
        book.setName("Тест");
        book.setDate(1990);
        book.setAuthor("Тест Тест Тест");
        booksService.save(book);
        assertEquals(1,booksRepository.findAll().size());
    }
    @Test
    public void whenGetAllBooksThenResultEquals0(){
        assertEquals(0,booksRepository.findAll().size());
    }
    @Test
    public void whenInsertBookAndDeleteBookThenSizeOfAllBooksEquals0(){
        Book book = new Book();
        book.setName("Тест");
        book.setDate(1990);
        book.setAuthor("Тест Тест Тест");
        booksService.save(book);
        assertEquals(1,booksRepository.findAll().size());
        booksService.delete(book.getId());
        assertEquals(0,booksRepository.findAll().size());
    }
    @Test
    public void whenInsertBookWithBadDateThenThrowException(){
        Book book = new Book();
        book.setName("Тест");
        book.setDate(2922);
        book.setAuthor("Тест Тест Тест");
        assertThrows(RuntimeException.class,()->booksService.save(book));
    }
    @Test
    public void whenInsertBookWithBadNameThenThrowException(){
        Book book = new Book();
        book.setName("Test");
        book.setDate(2922);
        book.setAuthor("Тест Тест Тест");
        assertThrows(RuntimeException.class,()->booksService.save(book));
    }
    @Test
    public void whenInsertBookWithAuthorInEnLanguageThenThrowException(){
        Book book = new Book();
        book.setName("Тест");
        book.setDate(1990);
        book.setAuthor("Test Test");
        assertThrows(RuntimeException.class,()->booksService.save(book));
    }
    @Test
    public void whenUpdateNameForBookThenNameEqualsTestOne(){
        Book book = new Book();
        book.setId(1);
        book.setName("Тест");
        book.setDate(1990);
        book.setAuthor("Тест Тест Тест");
        booksService.save(book);
        Optional<Book>savedBook = booksRepository.findById(book.getId());
        assertTrue(savedBook.isPresent());
        Book saved = savedBook.get();
        saved.setName("Тест Один");
        booksService.update(saved.getId(),saved);
        Optional<Book>updatedBook = booksRepository.findById(book.getId());
        assertTrue(updatedBook.isPresent());
        assertEquals("Тест Один",updatedBook.get().getName());
    }
}
