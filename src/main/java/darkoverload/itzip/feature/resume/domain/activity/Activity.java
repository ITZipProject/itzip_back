package darkoverload.itzip.feature.resume.domain.activity;

import darkoverload.itzip.feature.resume.dto.activity.ActivityDto;
import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    // 이력서
    private ResumeEntity resume;

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

    public static Activity update(ActivityDto activity, ResumeEntity resume){
        return Activity.builder()
                .name(activity.getName())
                .content(activity.getContent())
                .startDate(activity.getStartDate())
                .endDate(activity.getEndDate())
                .resume(resume)
                .activityId(activity.getActivityId())
                .build();
    }

    public ActivityEntity toEntity() {
        return ActivityEntity.builder()
                .resume(this.resume)
                .name(this.name)
                .content(this.content)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .id(this.activityId)
                .build();
    }
}
