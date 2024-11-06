package darkoverload.itzip.feature.resume.domain.myskill;

import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.language.Languages;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.myskill.MySkillsDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class MySkills {
    private final List<MySkill> mySkills;

    public MySkills() {
        this(new ArrayList<>());
    }

    public MySkills(List<MySkill> mySkills) {
        this.mySkills = mySkills;
    }

    public static Optional<MySkills> of(List<MySkillsDto> mySkills, Resume resume) {
        if (mySkills != null && isValidate(mySkills)) {
            return Optional.of(new MySkills(parse(mySkills, resume)));
        }

        return Optional.empty();
    }

    public static Optional<MySkills> of(List<MySkill> mySkills) {
        return Optional.of(new MySkills(mySkills));
    }

    public static List<MySkill> parse(List<MySkillsDto> mySkills, Resume resume) {
        return mySkills.stream()
                .map(createMySkillsDto -> {
                    MySkill mySkill = createMySkillsDto.create();
                    mySkill.setResume(resume);
                    return mySkill;
                }).toList();
    }

    public static boolean isValidate(List<MySkillsDto> mySkills) {
        return !mySkills.isEmpty();
    }
}
