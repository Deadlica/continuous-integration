package CIserver.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


// Spring Boot Application class that runs the CI-server
@SpringBootApplication
@ComponentScan(basePackages = {"CIserver.rest"})
public class CiServerApplication {

  public static void main(String[] args) {
    // Run the CI-server
    SpringApplication.run(CiServerApplication.class, args);
  }
}
