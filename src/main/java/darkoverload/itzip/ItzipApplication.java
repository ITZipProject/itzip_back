package darkoverload.itzip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ItzipApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItzipApplication.class, args);
    }

}
