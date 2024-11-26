package darkoverload.itzip.feature.resume.service.resume.port.career;

import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import java.util.List;

public interface CareerRepository {

    Career save(Career career);

    List<Career> update(List<Career> careers);

    List<Career> saveAll(List<Career> careers);

    void deleteAllById(List<Long> ids);

    void deleteAllCareers(List<Career> deleteCareers);

}
