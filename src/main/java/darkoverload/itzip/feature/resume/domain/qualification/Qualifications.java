package darkoverload.itzip.feature.resume.domain.qualification;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.qualification.QualificationDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class Qualifications {
    private final List<Qualification> qualifications;

    public Qualifications() {
        this(new ArrayList<>());
    }

    public Qualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public static Optional<Qualifications> of(List<QualificationDto> qualifications, Resume resume) {
        if (qualifications != null && isValidate(qualifications)) {
            return Optional.of(new Qualifications(parse(qualifications, resume)));
        }

        return Optional.empty();
    }

    public static Optional<Qualifications> of(List<Qualification> qualifications) {
        return Optional.of(new Qualifications(qualifications));
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
