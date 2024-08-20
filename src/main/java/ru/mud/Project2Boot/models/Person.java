package ru.mud.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
    public Person(Integer id, String info, int birthday) {
        this.id = id;
        this.info = info;
        this.birthday = birthday;
    }
}
