package darkoverload.itzip.feature.school.repository;

import darkoverload.itzip.feature.school.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long>, CustomSchoolRepository {
    @Override
    Long getTotalCount();

    @Override
    List<String> searchBySchoolName(String schoolName);
}
