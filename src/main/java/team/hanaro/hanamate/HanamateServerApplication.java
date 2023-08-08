package team.hanaro.hanamate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanamateServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanamateServerApplication.class, args);
    }

}