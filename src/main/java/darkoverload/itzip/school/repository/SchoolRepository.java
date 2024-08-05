package darkoverload.itzip.school.repository;

import darkoverload.itzip.school.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long>, CustomSchoolRepository {
    @Override
    Long getTotalCount();
}
