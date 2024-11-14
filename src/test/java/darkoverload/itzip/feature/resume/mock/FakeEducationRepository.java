package darkoverload.itzip.feature.resume.mock;

import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.service.resume.port.EducationRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeEducationRepository implements EducationRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Education> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<Education> findAllByResumeId(Long resumeId) {
        return data.stream()
                .filter(education -> Objects.equals(education.getResume().getResumeId(), resumeId))
                .toList();
    }

    @Override
    public List<Education> update(List<Education> educations) {
        return saveAll(educations);
    }

    @Override
    public Education save(Education education) {
        if (education.getEducationId() == null || education.getEducationId() == 0) {
            Education newEducation = Education.builder()
                    .educationId(autoGeneratedId.incrementAndGet())
                    .resume(education.getResume())
                    .major(education.getMajor())
                    .schoolName(education.getSchoolName())
                    .startDate(education.getStartDate())
                    .endDate(education.getEndDate())
                    .build();
            data.add(newEducation);

            return newEducation;
        }

        data.removeIf(item -> Objects.equals(item.getEducationId(), education.getEducationId()));
        data.add(education);

        return education;
    }

    @Override
    public List<Education> saveAll(List<Education> educations) {
        return educations.stream().map(this::save).toList();
    }

    @Override
    public void deleteAllEducations(List<Education> deleteEducations) {
        deleteEducations.stream().map(Education::getEducationId)
                .forEach(id -> data.removeIf(data -> data.getEducationId().equals(id)));
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        for (Long id : ids) {
            data.removeIf(item -> Objects.equals(item.getEducationId(), id));
        }
    }

    private List<Long> getEducationDeleteIds(List<Education> educations, Resume resume) {
        List<Long> educationIds = getEducationIds(resume.getResumeId());
        List<Long> updateIds = getEducationUpdateIds(educations);

        return educationIds.stream().filter(id -> !updateIds.contains(id)).toList();
    }

    private List<Long> getEducationUpdateIds(List<Education> educations) {
        return educations.stream().filter(Objects::nonNull).map(Education::getEducationId).toList();
    }

    private List<Long> getEducationIds(Long resumeId) {
        return findAllByResumeId(resumeId).stream().map(Education::getEducationId).toList();
    }
}
