package by.tms.diplomrestc51;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DiplomRestC51Application {

    public static void main(String[] args) {
        SpringApplication.run(DiplomRestC51Application.class, args);
    }

}
