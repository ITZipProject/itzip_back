package darkoverload.itzip.feature.resume.domain.qualification;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.qualification.QualificationDto;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class Qualifications {
    private final List<Qualification> qualifications;

    public Qualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public static Optional<Qualifications> of(List<QualificationDto> qualifications, Resume resume) {
        if (isValidate(qualifications)) {
            return Optional.of(new Qualifications(parse(qualifications, resume)));
        }

        return Optional.empty();
    }

    public static List<Qualification> parse(List<QualificationDto> qualifications, Resume resume) {
        return qualifications.stream()
                .map(createQualification -> {
                    Qualification qualification = createQualification.create();
                    qualification.setResume(resume);
                    return qualification;
                }).toList();
    }

    public static boolean isValidate(List<QualificationDto> qualifications) {
        return !qualifications.isEmpty();
    }
}
