package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.resume.Resume;

import java.util.List;

public interface CareerRepository {

    List<Career> findAllByResumeId(Long resumeId);

    List<Career> update(List<Career> careers, Resume resume);

    List<Career> saveAll(List<Career> careers);

    void deleteAllById(List<Long> ids);
}
