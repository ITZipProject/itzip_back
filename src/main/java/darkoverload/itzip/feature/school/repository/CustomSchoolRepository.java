package darkoverload.itzip.feature.school.repository;

import darkoverload.itzip.feature.school.domain.School;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomSchoolRepository {
    Long getTotalCount();

    List<String> searchBySchoolName(String schoolName);

}
