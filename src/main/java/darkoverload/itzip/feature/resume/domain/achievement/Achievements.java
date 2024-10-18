package darkoverload.itzip.feature.resume.domain.achievement;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.achievement.AchievementDto;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class Achievements {
    private final List<Achievement> achievements;

    public Achievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public static Optional<Achievements> of(List<AchievementDto> achievements, Resume resume) {
        if (isValidate(achievements)) {
            return Optional.of(new Achievements(parse(achievements, resume)));
        }

        return Optional.empty();
    }

    public static List<Achievement> parse(List<AchievementDto> achievements, Resume resume) {
        return achievements.stream()
                .map(createAchievementDto -> {
                    Achievement achievement = createAchievementDto.create();
                    achievement.setResume(resume);
                    return achievement;
                }).toList();
    }

    public static boolean isValidate(List<AchievementDto> achievements) {
        return !achievements.isEmpty();
    }
}
