package ru.mud.Project2Boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mud.Project2Boot.models.Person;
import ru.mud.Project2Boot.services.PeopleService;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;
    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (!peopleService.getPersonByInfo(person.getInfo()).isEmpty()) {
            if (peopleService.getPersonByInfo(person.getInfo()).get().getId() != person.getId()) {
                errors.rejectValue("info", "", "Пользователь с такими данными уже существует");
            }
        }
    }
}