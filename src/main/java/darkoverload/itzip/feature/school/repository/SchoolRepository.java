package darkoverload.itzip.feature.school.repository;

import darkoverload.itzip.feature.school.entity.SchoolDocument;
import darkoverload.itzip.feature.school.entity.SchoolEntity;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@ExcludeFromJpaRepositories
public interface SchoolRepository extends ElasticsearchRepository<SchoolDocument, Long>{
   long count();

}
