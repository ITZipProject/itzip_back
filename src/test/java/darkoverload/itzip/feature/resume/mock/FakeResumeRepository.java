package darkoverload.itzip.feature.resume.mock;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.service.resume.port.ResumeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeResumeRepository implements ResumeRepository {
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Resume> data = Collections.synchronizedList(new ArrayList<>());


    @Override
    public Resume save(Resume resume) {
        if(resume.getResumeId() == null || resume.getResumeId() == 0) {
            Resume newResume = Resume.builder()
                    .resumeId(autoGeneratedId.incrementAndGet())
                    .email(resume.getEmail())
                    .phone(resume.getPhone())
                    .subject(resume.getSubject())
                    .introduction(resume.getIntroduction())
                    .publicOnOff(resume.getPublicOnOff())
                    .links(resume.getLinks())
                    .imageUrl(resume.getImageUrl())
                    .userId(resume.getUserId())
                    .build();
            data.add(newResume);
            return newResume;
        }

        data.removeIf(item -> Objects.equals(item.getResumeId(), resume.getResumeId()));
        data.add(resume);
        return resume;
    }

    @Override
    public Resume update(Resume resume) {
        return save(resume);
    }

    @Override
    public Resume getReferenceById(Long id) {
        return data.stream()
                .filter(resume -> Objects.equals(resume.getResumeId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Resume with id " + id));
    }
}
