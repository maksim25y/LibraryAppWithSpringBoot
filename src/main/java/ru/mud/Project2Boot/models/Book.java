package ru.mud.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@ToString
@NoArgsConstructor
@Setter
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "[а-яА-ЯёЁ\\s]+",message = "Название книги должно быть на русском языке")
    private String name;
    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+",
            message = "Информация об авторе должна быть в следующем формате: Фамилия Имя Отчество и на русском языке")
    private String author;
    @Min(value = 0,message = "Год выпуска не может быть меньше 0")
    @Max(value = 2024,message = "Год выпуска не может быть больше 2024")
    private int date;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Person person;
    @Getter
    @Column(name = "taken")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date taken;
    @Transient
    private boolean bad;

    public boolean isBad() {
        return bad;
    }
    public Book(Integer id, String name, String author, int date, Date taken) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.date = date;
        this.taken = taken;
    }

}
