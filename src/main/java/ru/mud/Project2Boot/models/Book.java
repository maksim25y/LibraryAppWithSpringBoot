package ru.mud.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "[а-яА-Я\\s]+",message = "Название книги должно быть на русском языке")
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
    @Column(name = "taken")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date taken;
    @Transient
    private Boolean bad;
}
