package darkoverload.itzip.feature.resume.domain.activity;

import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateActivity {

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


    public ActivityEntity toEntity() {
        return ActivityEntity.builder()
                .resume(this.resume)
                .name(this.name)
                .content(this.content)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
