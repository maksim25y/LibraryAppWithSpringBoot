import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mud.Project2Boot.Project2BootApplication;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.repositories.BooksRepository;
import ru.mud.Project2Boot.services.BooksService;
import ru.mud.Project2Boot.services.PeopleService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Project2BootApplication.class)
public class BooksServiceTests {
    @Autowired
    private BooksService booksService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private BooksRepository booksRepository;
    @BeforeEach
    public void cleanAllTable(){
        booksRepository.deleteAll();
    }
    @Test
    public void whenInsertBookThenItIsPresentInDatabase(){
        Book book = getDefaultBook();
        booksService.save(book);
        Optional<Book>savedBook = booksRepository.findById(book.getId());
        assertTrue(savedBook.isPresent());
    }
    @Test
    public void whenInsertBookThenSizeOfAllBooksEquals1(){
        Book book = getDefaultBook();
        booksService.save(book);
        assertEquals(1,booksRepository.findAll().size());
    }
    @Test
    public void whenGetAllBooksThenResultEquals0(){
        assertEquals(0,booksRepository.findAll().size());
    }
    @Test
    public void whenInsertBookAndDeleteBookThenSizeOfAllBooksEquals0(){
        Book book = getDefaultBook();
        booksService.save(book);
        assertEquals(1,booksRepository.findAll().size());
        booksService.delete(book.getId());
        assertEquals(0,booksRepository.findAll().size());
    }
    @Test
    public void whenInsertBookWithBadDateThenThrowException(){
        Book book = getDefaultBook();
        book.setDate(2922);
        assertThrows(RuntimeException.class,()->booksService.save(book));
    }
    @Test
    public void whenInsertBookWithBadNameThenThrowException(){
        Book book = getDefaultBook();
        book.setName("Test");
        assertThrows(RuntimeException.class,()->booksService.save(book));
    }
    @Test
    public void whenInsertBookWithAuthorInEnLanguageThenThrowException(){
        Book book = getDefaultBook();
        book.setAuthor("Test Test");
        assertThrows(RuntimeException.class,()->booksService.save(book));
    }
    @Test
    public void whenUpdateNameForBookThenNameEqualsTestOne(){
        Book book = getDefaultBook();
        booksService.save(book);
        Optional<Book>savedBook = booksService.findById(book.getId());
        assertTrue(savedBook.isPresent());
        Book saved = savedBook.get();
        saved.setName("Тест Один");
        booksService.update(saved.getId(),saved);
        Optional<Book>updatedBook = booksService.findById(book.getId());
        assertTrue(updatedBook.isPresent());
        assertEquals("Тест Один",updatedBook.get().getName());
    }
    @Test
    public void whenFindBookByPrefixAndBookDoesNotExistTheResultEqualsNull(){
        assertNull(booksService.getBooksByPrefix("Тест"));
    }
    @Test
    public void whenFindBookByPrefixAndBookExistTheResultNotNull(){
        Book book = getDefaultBook();
        booksService.save(book);
        assertNotNull(booksService.getBooksByPrefix("Тест"));
    }
    @Test
    public void whenFindBooksWithPaginationThenSizeOfResultEquals(){
        for(int i=0;i<100;i++){
            Book book = getDefaultBook();
            booksService.save(book);
        }
        assertEquals(100,booksRepository.findAll().size());
        assertEquals(2,booksService.findWithPagination(1,2,false).size());
    }
    @Test
    public void whenFindBooksSortedByDateFirstBookDateEquals1990(){
        Book book1 = getDefaultBook();
        Book book2 = getDefaultBook();
        book2.setDate(2000);
        booksService.save(book1);
        booksService.save(book2);
        List<Book>resultBooks = booksService.findWithPagination(1,1,true);
        assertFalse(resultBooks.isEmpty());
        assertEquals(1990,resultBooks.getFirst().getDate());
    }
    @Test
    public void whenFindBookByIdAndBookPresentInDatabaseResultIsNotNull(){
        Book book = getDefaultBook();
        book.setId(2);
        booksService.save(book);
        Optional<Book>savedBook = booksService.findById(book.getId());
        assertTrue(savedBook.isPresent());
    }
    @Test
    public void whenFindBookByIdAndBookDoesNotPresentInDatabaseResultIsNull(){
        Optional<Book>bookFromDatabase = booksService.findById(4);
        assertFalse(bookFromDatabase.isPresent());
    }
    @Test
    public void whenGetPersonByBookAndBookHasNotPersonResultEqualsNull(){
        Book book = getDefaultBook();
        book.setId(2);
        booksService.save(book);
        Optional<Book>savedBook = booksService.findById(book.getId());
        assertTrue(savedBook.isPresent());
        assertNull(savedBook.get().getPerson());

    }
    @Test
    public void whenGetPersonByBookAndBookHasPersonResultNotEqualsNull(){
        Book book = getDefaultBook();
        book.setId(2);
        booksService.save(book);
        Person person = new Person();
        person.setInfo("Тест Тест Тест");
        person.setBirthday(1990);
        peopleService.save(person);
        book.setPerson(person);
        booksService.save(book);
        Optional<Book>savedBook = booksService.findById(book.getId());
        assertTrue(savedBook.isPresent());
        assertNotNull(savedBook.get().getPerson());
    }
    private Book getDefaultBook(){
        Book book = new Book();
        book.setName("Тест");
        book.setDate(1990);
        book.setAuthor("Тест Тест Тест");
        return book;
    }
}
