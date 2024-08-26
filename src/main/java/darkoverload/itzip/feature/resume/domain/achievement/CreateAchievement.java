package darkoverload.itzip.feature.resume.domain.achievement;


import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAchievement {

    // 이력서
    private ResumeEntity resume;

    // 수상명
    private String name;

    // 수상기관
    private String organization;

    // 수상일
    private LocalDateTime achievementDate;

    // 설명
    private String content;

    public AchievementEntity toEntity() {
        return AchievementEntity.builder()
                .resume(this.resume)
                .name(this.name)
                .organization(this.organization)
                .achievementDate(this.achievementDate)
                .content(this.content)
                .build();
    }
}
