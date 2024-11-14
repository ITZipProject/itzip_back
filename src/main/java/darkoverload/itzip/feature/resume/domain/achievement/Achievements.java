package darkoverload.itzip.feature.resume.domain.achievement;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.achievement.AchievementDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class Achievements {
    private final List<Achievement> achievements;

    public Achievements() {
        this(new ArrayList<>());
    }

    public Achievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public static Optional<Achievements> of(List<AchievementDto> achievements, Resume resume) {
        if (achievements != null && isValidate(achievements)) {
            return Optional.of(new Achievements(parse(achievements, resume)));
        }

        return Optional.empty();
    }

    public static Optional<Achievements> of(List<Achievement> achievements) {
        return Optional.of(new Achievements(achievements));
    }

    public static List<Achievement> parse(List<AchievementDto> achievements, Resume resume) {
        return achievements.stream()
                .map(achievementDto -> {
                    Achievement achievement = achievementDto.toModel();
                    achievement.setResume(resume);
                    return achievement;
                }).toList();
    }

    public static boolean isValidate(List<AchievementDto> achievements) {
        return !achievements.isEmpty();
    }

    public List<Achievement> deleteAchievements(List<Achievement> allAchievements) {
        return achievements.stream().filter(achievement -> {
            return !allAchievements.contains(achievement);
        }).toList();
    }

}
