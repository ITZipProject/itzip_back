package darkoverload.itzip.feature.resume.repository.career.Custom;

import darkoverload.itzip.feature.resume.entity.CareerEntity;

import java.util.List;

public interface CustomCareerRepository {

    List<CareerEntity> findAllByResumeId(Long resumeId);
}
