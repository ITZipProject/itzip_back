package darkoverload.itzip.feature.resume.domain.achievement;


import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.achievement.AchievementDto;
import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Achievement {

    // 이력서
    private Resume resume;

    // 수상명
    private final String name;

    // 수상기관
    private final String organization;

    // 수상일
    private final LocalDateTime achievementDate;

    // 설명
    private final String content;

    // 아이디값
    private final Long achievementId;

    @Builder
    public Achievement(Resume resume, String name, String organization, LocalDateTime achievementDate, String content, Long achievementId) {
        this.resume = resume;
        this.name = name;
        this.organization = organization;
        this.achievementDate = achievementDate;
        this.content = content;
        this.achievementId = achievementId;
    }

    public static Achievement update(AchievementDto achievementDto, Resume resume) {
        return Achievement.builder()
                .resume(resume)
                .name(achievementDto.getName())
                .organization(achievementDto.getOrganization())
                .content(achievementDto.getContent())
                .achievementId(achievementDto.getAchievementId())
                .build();

    }

    public AchievementEntity toEntity() {
        return AchievementEntity.builder()
                .resume(this.resume.toEntity())
                .name(this.name)
                .organization(this.organization)
                .achievementDate(this.achievementDate)
                .content(this.content)
                .id(this.achievementId)
                .build();
    }

}
