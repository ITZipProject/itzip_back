package darkoverload.itzip.feature.resume.dto.achievement;

import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "수상 이름", example = "Hackathon Winner")
    private String name;

    // 수상기관
    @Schema(description = "수상 조직", example = "Global Hackathon 2024")
    private String organization;

    // 수상일
    @Schema(description = "수상 날짜", example = "2024-11-27T05:51:54.050Z")
    private LocalDateTime achievementDate;

    // 설명
    @Size(max = 255, message = "내용을 255 글자 이상 입력 할 수 없습니다.")
    @Schema(description = "내용", example = "Developed an award-winning AI-powered application.")
    private String content;

    // id 값
    @Schema(description = "수상 아이디 값", example = "이력서 생성 시 제외 업데이트 시 추가해서 사용")
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
