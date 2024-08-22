package darkoverload.itzip.feature.resume.dto.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
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


    public Achievement toDomain() {
        return Achievement.builder()
                .name(name)
                .organization(organization)
                .achievementDate(achievementDate)
                .content(content)
                .build();
    }
}
