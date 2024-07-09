package darkoverload.itzip.sample.Repository;

import darkoverload.itzip.sample.domain.SampleRedis;
import org.springframework.data.repository.CrudRepository;

public interface SampleRedisRepository extends CrudRepository<SampleRedis, String> {

}
