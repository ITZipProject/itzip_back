package darkoverload.itzip.resume.repository;

import darkoverload.itzip.resume.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long>, CustomSchoolRepository {
    @Override
    Long getTotalCount();
}
