package kgrzesiak.webapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Project4Application {

	private static ApplicationContext appContext;

	public static void main(String[] args) {

		appContext = SpringApplication.run(Project4Application.class, args);


	}

}
