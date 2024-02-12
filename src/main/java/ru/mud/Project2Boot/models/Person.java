package ru.mud.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+",
            message = "Информация о вас должна быть в следующем формате: Фамилия Имя Отчество и на русском языке")
    private String info;
    @Min(value = 1901,message = "Год вашего рождения не может быть меньше 1900")
    @Max(value = 2024,message = "Год вашего рождения не может быть больше 2024")
    private int birthday;
    @OneToMany(mappedBy = "person",fetch = FetchType.LAZY)
    @OrderBy(value = "taken DESC")
    private final List<Book>bookList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public Person() {
    }

    public Person(int id, String info, int birthday) {
        this.id = id;
        this.info = info;
        this.birthday = birthday;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
