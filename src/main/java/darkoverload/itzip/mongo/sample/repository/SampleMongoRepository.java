package darkoverload.itzip.mongo.sample.repository;

import darkoverload.itzip.global.config.mongo.ExcludeFromJpaRepositories;
import darkoverload.itzip.mongo.sample.domain.SampleMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

@ExcludeFromJpaRepositories
public interface SampleMongoRepository extends MongoRepository<SampleMongo, String> {
}
