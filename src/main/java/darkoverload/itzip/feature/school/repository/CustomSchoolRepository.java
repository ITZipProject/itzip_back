package darkoverload.itzip.feature.school.repository;

import java.util.List;

public interface CustomSchoolRepository {
    Long getTotalCount();

    List<String> searchBySchoolName(String schoolName);
}
