package darkoverload.itzip.feature.resume.domain.education;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class Educations {
    private final List<Education> educations;

    public Educations(List<Education> educations) {
        this.educations = educations;
    }

    public static Optional<Educations> of(List<EducationDto> educations, Resume resume) {
        if (isValidate(educations)) {
           return Optional.of(new Educations(parse(educations, resume)));
        }

        return Optional.empty();
    }

    public static List<Education> parse(List<EducationDto> educations, Resume resume) {
        return educations.stream()
                .map(educationDto -> {
                    Education education = educationDto.create();
                    education.setResume(resume);
                    return education;
                }).toList();
    }

    public static boolean isValidate(List<EducationDto> educations) {
        return !educations.isEmpty();
    }
}
