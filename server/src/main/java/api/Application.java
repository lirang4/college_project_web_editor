package api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Collections;


@SpringBootApplication
@EnableMongoAuditing
public class Application implements CommandLineRunner {

//    @Autowired
//    private UsersRepository repository;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "3000"));
        app.run(args);
    }

    @Override
    public void run(String... args) {
//        repository.deleteAll();
//
//        // save a couple of customers
//        repository.save(new User("lirang", "lirang@gmail.com", "liran1234", "liran", "glikman"));
//        repository.save(new User("itaybn", "itaybn@gmail.com", "itay1234", "itay", "barnisim"));
    }
}