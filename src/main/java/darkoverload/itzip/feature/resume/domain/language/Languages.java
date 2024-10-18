package darkoverload.itzip.feature.resume.domain.language;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class Languages {
    private final List<Language> languages;

    public Languages(List<Language> languages) {
        this.languages = languages;
    }

    public static Optional<Languages> of(List<LanguageDto> languages, Resume resume) {
        if (isValidate(languages)) {
            return Optional.of(new Languages(parse(languages, resume)));
        }

        return Optional.empty();
    }

    public static List<Language> parse(List<LanguageDto> languages, Resume resume) {
        return languages.stream()
                .map(createLanguageDto -> {
                    Language education = createLanguageDto.create();
                    education.setResume(resume);
                    return education;
                }).toList();
    }

    public static boolean isValidate(List<LanguageDto> languages) {
        return !languages.isEmpty();
    }
}
