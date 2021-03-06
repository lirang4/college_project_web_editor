package analyzer.repositories;

import analyzer.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UsersRepository extends MongoRepository<User, String> {

    User findByUserNameAndPassword(String userName, String password);
}