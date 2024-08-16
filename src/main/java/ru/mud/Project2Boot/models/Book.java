package ru.mud.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    private boolean bad;

    public boolean isBad() {
        return bad;
    }

    public void setBad(boolean bad) {
        this.bad = bad;
    }

    public Book(){

    }

    public Date getTaken() {
        return taken;
    }

    public void setTaken(Date taken) {
        this.taken = taken;
    }


    public Book(int id, String name, String author, int date, Date taken) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.date = date;
        this.taken = taken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                '}';
    }
}
