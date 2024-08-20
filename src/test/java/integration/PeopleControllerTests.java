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
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.services.PeopleService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class PeopleControllerTests {
    private final String PEOPLE_URL = "/people";
    private final String VIEWS_PEOPLE = "views/people/";
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
    private Person getDefaultPerson(){
        return new Person(1,"Иванов Иван Иванович",1990);
    }
}
