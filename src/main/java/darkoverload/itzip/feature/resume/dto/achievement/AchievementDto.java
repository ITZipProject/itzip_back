package darkoverload.itzip.feature.resume.dto.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDto {


    // 수상명
    private String name;

    // 수상기관
    private String organization;

    // 수상일
    private LocalDateTime achievementDate;

    // 설명
    @Size(max = 255, message = "내용을 255 글자 이상 입력 할 수 없습니다.")
    private String content;

    // id 값
    private Long achievementId;

    public AchievementDto(String name, String organization, LocalDateTime achievementDate, String content) {
        this.name = name;
        this.organization = organization;
        this.achievementDate = achievementDate;
        this.content = content;
    }

    public Achievement toModel() {
        return Achievement.builder()
                .achievementId(this.achievementId)
                .name(this.name)
                .organization(this.organization)
                .achievementDate(this.achievementDate)
                .content(this.content)
                .build();
    }

}
