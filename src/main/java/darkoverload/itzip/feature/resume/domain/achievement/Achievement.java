package darkoverload.itzip.feature.resume.domain.achievement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {

    // 수상명
    private String name;

    // 수상기관
    private String organization;

    // 수상일
    private LocalDateTime achievementDate;

    // 설명
    private String content;

}
