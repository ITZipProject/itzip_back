package darkoverload.itzip.feature.resume.domain.activity;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.activity.ActivityDto;
import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
public class Activity {

    // 이력서
    private Resume resume;

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

    @Builder
    public Activity(Resume resume, String name, String content, LocalDateTime startDate, LocalDateTime endDate, Long activityId) {
        this.resume = resume;
        this.name = name;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityId = activityId;
    }

    public static Activity update(ActivityDto activity, Resume resume){
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
                .resume(this.resume.toEntity())
                .name(this.name)
                .content(this.content)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .id(this.activityId)
                .build();
    }

    @Override
    public String toString() {
        return "Activity{" +
                "resume=" + resume +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", activityId=" + activityId +
                '}';
    }
}
