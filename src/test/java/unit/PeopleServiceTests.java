package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mud.Project2Boot.Project2BootApplication;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.repositories.PeopleRepository;
import ru.mud.Project2Boot.services.PeopleService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Project2BootApplication.class)
public class PeopleServiceTests {
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private PeopleRepository peopleRepository;
    @BeforeEach
    public void cleanAllTable(){
        peopleRepository.deleteAll();
    }
    @Test
    public void whenInsertPersonThenItIsPresentInDatabase(){
        Person person = getDefaultPerson();
        peopleService.save(person);
        Optional<Person>personOptional = peopleRepository.findById(person.getId());
        assertTrue(personOptional.isPresent());
    }
    @Test
    public void whenInsertPersonThenSizeOfAllBooksEquals1(){
        Person person = getDefaultPerson();
        peopleService.save(person);
        assertEquals(1,peopleRepository.findAll().size());
    }
    @Test
    public void whenGetPeopleThenResultEquals0(){
        assertEquals(0,peopleRepository.findAll().size());
    }
    @Test
    public void whenInsertPersonAndDeletePersonThenSizeOfPeopleEquals0(){
        Person person = getDefaultPerson();
        peopleService.save(person);
        assertEquals(1,peopleRepository.findAll().size());
        peopleService.delete(person.getId());
        assertEquals(0,peopleRepository.findAll().size());
    }
    @Test
    public void whenInsertPersonWithBadBirthdayThenThrowException(){
        Person person = getDefaultPerson();
        person.setBirthday(2100);
        assertThrows(RuntimeException.class,()->peopleService.save(person));
    }
    @Test
    public void whenInsertPersonWithInfoWithBadFormatThenThrowException(){
        Person person = getDefaultPerson();
        person.setInfo("Иванов Иван");
        assertThrows(RuntimeException.class,()->peopleService.save(person));
    }
    @Test
    public void whenInsertPersonWithInfoInNotRussianLanguageThenThrowException(){
        Person person = getDefaultPerson();
        person.setInfo("Ivanov Ivan Jovanovich");
        assertThrows(RuntimeException.class,()->peopleService.save(person));
    }
    @Test
    public void whenUpdateInfoForPersonThenNameEqualsIvanovIvanRomanovic(){
        Person person = getDefaultPerson();
        peopleService.save(person);
        Optional<Person>savedPerson = peopleService.findById(person.getId());
        assertTrue(savedPerson.isPresent());
        Person saved = savedPerson.get();
        saved.setInfo("Иванов Иван Романович");
        peopleService.update(saved.getId(),saved);
        Optional<Person>updatedPerson = peopleService.findById(saved.getId());
        assertTrue(updatedPerson.isPresent());
        assertEquals("Иванов Иван Романович",updatedPerson.get().getInfo());
    }

    private Person getDefaultPerson(){
        Person person = new Person();
        person.setInfo("Иванов Иван Иванович");
        person.setBirthday(2000);
        return person;
    }
}
