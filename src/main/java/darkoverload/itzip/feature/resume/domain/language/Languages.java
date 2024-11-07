package darkoverload.itzip.feature.resume.domain.language;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
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
public class Languages {
    private final List<Language> languages;

    public Languages() {
        this(new ArrayList<>());
    }

    public Languages(List<Language> languages) {
        this.languages = languages;
    }

    public static Optional<Languages> of(List<LanguageDto> languages, Resume resume) {
        if (languages != null && isValidate(languages)) {
            return Optional.of(new Languages(parse(languages, resume)));
        }

        return Optional.empty();
    }

    public static Optional<Languages> of(List<Language> languages) {
        return Optional.of(new Languages(languages));
    }

    public static List<Language> parse(List<LanguageDto> languages, Resume resume) {
        return languages.stream()
                .map(languageDto -> {
                    Language education = languageDto.toModel();
                    education.setResume(resume);
                    return education;
                }).toList();
    }

    public static boolean isValidate(List<LanguageDto> languages) {
        return !languages.isEmpty();
    }

    public List<Language> deleteLanguages(List<Language> allLanguages) {
        return languages.stream().filter(language -> {
            return !allLanguages.contains(language);
        }).collect(Collectors.toList());
    }

}
