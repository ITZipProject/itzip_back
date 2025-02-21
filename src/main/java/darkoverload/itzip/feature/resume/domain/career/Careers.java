package darkoverload.itzip.feature.resume.domain.career;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.ResumeWorkTermType;
import darkoverload.itzip.feature.resume.dto.career.CareerDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode
public class Careers {
    private static final long MIN_WORK_PERIOD = 0L;

    private final List<Career> careers;

    public Careers() {
        this(new ArrayList<>());
    }

    public Careers(List<Career> careers) {
        this.careers = careers;
    }

    public static Optional<Careers> of(List<CareerDto> careers, Resume resume) {
        if (careers != null && isValidate(careers)) {
            return Optional.of(new Careers(parse(careers, resume)));
        }

        return Optional.empty();
    }

    public static Optional<Careers> of(List<Career> careers) {
        return Optional.of(new Careers(careers));
    }

    public static List<Career> parse(List<CareerDto> careers, Resume resume) {
        return careers.stream()
                .map(careerDto -> {
                    Career career = careerDto.toModel();
                    career.setResume(resume);
                    return career;
                }).toList();
    }

    public static boolean isValidate(List<CareerDto> careers) {
        return !careers.isEmpty();
    }

    public List<Career> deleteCareers(List<Career> allCareers) {
        return careers.stream().filter(career -> {
                    return !allCareers.contains(career);
                }).collect(Collectors.toList());
    }

    public List<Resume> searchResumeMakeWorkPeriod(Map<Long, Resume> resumeMaps) {
        List<Resume> resumes = new ArrayList<>();
        for(Long key : resumeMaps.keySet()) {
            long totalWorkLongTerm = getTotalCareerPeriod(key);
            long year = totalWorkLongTerm / 12;
            Resume resume = resumeMaps.get(key);
            // PublicOnOff 제거하고 주는 부분
            resumes.add(Resume.searchResume(resume, ResumeWorkTermType.from(year).getName()));
        }
        return resumes;
    }

    private long getTotalCareerPeriod(Long key) {
        if(careers.isEmpty()) {
            return MIN_WORK_PERIOD;
        }

        return careers.stream().filter(career -> career.isResumeIdEquals(key)).mapToLong(Career::workTerm).sum();
    }
}
