package darkoverload.itzip.feature.school.repository;

import darkoverload.itzip.feature.school.entity.SchoolDocument;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

@ExcludeFromJpaRepositories
public interface SchoolRepository extends ElasticsearchRepository<SchoolDocument, Long>{
   long count();

}
