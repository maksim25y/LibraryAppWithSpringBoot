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
import ru.mud.Project2Boot.controllers.PeopleController;
import ru.mud.Project2Boot.models.Book;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.services.PeopleService;
import ru.mud.Project2Boot.util.PersonValidator;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PeopleControllerTests {
    private final String PEOPLE_URL = "/people";
    private final String VIEWS_PEOPLE = "views/people/";
    @Mock
    private PersonValidator personValidator;
    @Mock
    private PeopleService peopleService;
    @InjectMocks
    private PeopleController peopleController;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(peopleController).build();
    }
    @Test
    public void whenGetAllPeopleThenAddAttributePeopleInModel() throws Exception {
        List<Person>people = List.of(getDefaultPerson());
        when(peopleService.findAll()).thenReturn(people);
        mockMvc.perform(MockMvcRequestBuilders.get(PEOPLE_URL)).andExpect(status().isOk())
                .andExpect(view().name(VIEWS_PEOPLE+"index"))
                .andExpect(MockMvcResultMatchers.model().attribute("people", people));;
    }
    @Test
    public void shouldGetBooksNewAndAttributeBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PEOPLE_URL+"/new")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_PEOPLE+"new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("person"));
    }
    @Test
    public void shouldReturnPersonWithBooksWhenBooksCollectionIsNotEmpty() throws Exception {
        Person person = getDefaultPerson();
        List<Book>books = List.of(new Book());
        when(peopleService.findById(person.getId())).thenReturn(Optional.of(person));
        when(peopleService.getBooksByPersonId(person.getId())).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get(PEOPLE_URL+"/{id}", person.getId()).accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_PEOPLE+"show"))
                .andExpect(MockMvcResultMatchers.model().attribute("person",person))
                .andExpect(MockMvcResultMatchers.model().attribute("books",books));
    }
    @Test
    public void shouldReturnPersonWithNotBooksWhenBooksCollectionIsEmpty() throws Exception {
        Person person = getDefaultPerson();
        List<Book>books = List.of();
        when(peopleService.findById(person.getId())).thenReturn(Optional.of(person));
        when(peopleService.getBooksByPersonId(person.getId())).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get(PEOPLE_URL+"/{id}", person.getId()).accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_PEOPLE+"show"))
                .andExpect(MockMvcResultMatchers.model().attribute("person",person))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("books"));
    }
    @Test
    public void shouldHasAttributePersonWhenGetEditPerson() throws Exception {
        Person person = getDefaultPerson();
        when(peopleService.findById(person.getId())).thenReturn(Optional.of(person));
        mockMvc.perform(MockMvcRequestBuilders.get(PEOPLE_URL+"/{id}/edit", person.getId())
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_PEOPLE+"edit"))
                .andExpect(MockMvcResultMatchers.model().attribute("person",person));
    }
    @Test
    public void createPersonValid() throws Exception {
        Person person = getDefaultPerson();
        person.setId(null);
        mockMvc.perform(MockMvcRequestBuilders.post(PEOPLE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("info", person.getInfo())
                        .param("birthday", String.valueOf(person.getBirthday())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PEOPLE_URL));
    }
    @Test
    public void createPersonNotValid() throws Exception {
        Person person = getDefaultPerson();
        person.setId(null);
        person.setBirthday(2090);
        mockMvc.perform(MockMvcRequestBuilders.post(PEOPLE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("info", person.getInfo())
                        .param("birthday", String.valueOf(person.getBirthday())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(VIEWS_PEOPLE+"new"));
        verify(peopleService, never()).save(any(Person.class));

    }
    private Person getDefaultPerson(){
        return new Person(1,"Иванов Иван Иванович",1990);
    }
}
