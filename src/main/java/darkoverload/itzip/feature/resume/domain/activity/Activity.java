package darkoverload.itzip.feature.resume.domain.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    // 활동명
    private String name;

    // 내용
    private String content;

    // 시작일
    private LocalDateTime startDate;

    // 종료일
    private LocalDateTime endDate;


}
