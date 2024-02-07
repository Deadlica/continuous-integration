package CIserver.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"CIserver.rest"})
public class CiServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CiServerApplication.class, args);
  }
}
