package darkoverload.itzip.feature.resume.dto.activity;

import darkoverload.itzip.feature.resume.domain.activity.Activity;
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
    private String name;

    // 내용
    private String content;

    // 시작일
    private LocalDateTime startDate;

    // 종료일
    private LocalDateTime endDate;

    // 아이디
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
