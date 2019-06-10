package analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import java.util.Collections;

@SpringBootApplication
@EnableMongoAuditing
public class Application {

//    @Autowired
//    private UsersRepository repository;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "3000"));
        app.run(args);
    }

}