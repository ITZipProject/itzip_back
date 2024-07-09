package darkoverload.itzip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "darkoverload.itzip.postgresql")
@EnableMongoRepositories(basePackages = "darkoverload.itzip.mongo")
public class ItzipApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItzipApplication.class, args);
    }

}
