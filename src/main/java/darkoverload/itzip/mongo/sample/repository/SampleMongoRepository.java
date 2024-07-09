package darkoverload.itzip.mongo.sample.repository;

import darkoverload.itzip.mongo.sample.domain.SampleMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleMongoRepository extends MongoRepository<SampleMongo, String> {
}
