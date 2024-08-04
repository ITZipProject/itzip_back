package darkoverload.itzip.sample.Repository;

import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import darkoverload.itzip.sample.domain.SampleRedis;
import org.springframework.data.repository.CrudRepository;

@ExcludeFromJpaRepositories
public interface SampleRedisRepository extends CrudRepository<SampleRedis, String> {

}
