package darkoverload.itzip.feature.resume.domain.education;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode
public class Educations {
    private final List<Education> educations;

    public Educations() {
        this(new ArrayList<>());
    }

    public Educations(List<Education> educations) {
        this.educations = educations;
    }

    public static Optional<Educations> of(List<EducationDto> educations, Resume resume) {
        if (educations != null && isValidate(educations)) {
            return Optional.of(new Educations(parse(educations, resume)));
        }

        return Optional.empty();
    }

    public static Optional<Educations> of(List<Education> educations) {
        return Optional.of(new Educations(educations));
    }

    public static List<Education> parse(List<EducationDto> educations, Resume resume) {
        return educations.stream()
                .map(educationDto -> {
                    Education education = educationDto.toModel();
                    education.setResume(resume);
                    return education;
                }).toList();
    }

    public static boolean isValidate(List<EducationDto> educations) {
        return !educations.isEmpty();
    }

    public List<Education> deleteEducations(List<Education> allEducations) {
        return educations.stream().filter(education -> {
            return !allEducations.contains(education);
        }).collect(Collectors.toList());
    }

}
