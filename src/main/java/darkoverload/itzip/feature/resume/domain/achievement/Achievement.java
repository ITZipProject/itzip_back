package darkoverload.itzip.feature.resume.domain.achievement;


import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.achievement.AchievementDto;
import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {

    // 이력서
    private Resume resume;

    // 수상명
    private String name;

    // 수상기관
    private String organization;

    // 수상일
    private LocalDateTime achievementDate;

    // 설명
    private String content;

    // 아이디값
    private Long achievementId;

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
