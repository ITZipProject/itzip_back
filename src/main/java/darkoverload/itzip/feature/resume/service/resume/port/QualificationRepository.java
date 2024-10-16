package darkoverload.itzip.feature.resume.service.resume.port;

import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.domain.resume.Resume;

import java.util.List;

public interface QualificationRepository {
    List<Qualification> findByAllResumeId(Long resumeId);

    List<Qualification> update(List<Qualification> qualifications, Resume resume);

    List<Qualification> saveAll(List<Qualification> qualifications);

    void deleteAllById(List<Long> ids);
}
