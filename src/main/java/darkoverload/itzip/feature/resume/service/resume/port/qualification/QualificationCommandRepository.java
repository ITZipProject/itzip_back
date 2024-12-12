package darkoverload.itzip.feature.resume.service.resume.port.qualification;

import darkoverload.itzip.feature.resume.domain.qualification.Qualification;

import java.util.List;

public interface QualificationCommandRepository {

    List<Qualification> update(List<Qualification> qualifications);

    Qualification save(Qualification qualification);

    List<Qualification> saveAll(List<Qualification> qualifications);

    void deleteAllById(List<Long> ids);

    void deleteAllQualifications(List<Qualification> deleteQualifications);
}
