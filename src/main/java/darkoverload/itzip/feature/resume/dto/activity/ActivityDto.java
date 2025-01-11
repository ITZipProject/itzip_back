package darkoverload.itzip.feature.resume.dto.activity;

import darkoverload.itzip.feature.resume.domain.activity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {

    // 활동명
    @Schema(description = "활동 이름", example = "Open Source Contribution")
    private String name;

    // 내용
    @Schema(description = "활동 내용", example = "Contributed to Apache Kafka project.")
    private String content;

    // 시작일
    @Schema(description = "시작 날짜", example = "2023-01-01T00:00:00.000Z")
    private LocalDateTime startDate;

    // 종료일
    @Schema(description = "종료 날짜", example = "2023-12-31T23:59:59.999Z")
    private LocalDateTime endDate;

    // 아이디
    @Schema(description = "활동 아이디 값", example = "이력서 생성 시 제외 업데이트 시 추가해서 사용")
    private Long activityId;

    public ActivityDto(String name, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Activity toModel(){
        return Activity.builder()
                .activityId(this.activityId)
                .name(this.name)
                .content(this.content)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

}
