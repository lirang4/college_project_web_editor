package analyzer.repositories;

import analyzer.models.Code;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CodesRepository extends MongoRepository<Code, String> {

    List<Code> findByUserName(String userName);
}