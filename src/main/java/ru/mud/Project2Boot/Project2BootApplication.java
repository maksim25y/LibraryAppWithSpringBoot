package ru.mud.Project2Boot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootApplication
public class Project2BootApplication {
	public static void main(String[] args) {
		SpringApplication.run(Project2BootApplication.class, args);
	}

}
