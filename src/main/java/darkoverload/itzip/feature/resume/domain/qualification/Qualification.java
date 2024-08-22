package darkoverload.itzip.feature.resume.domain.qualification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Qualification {

    // 발급기관
    private String organization;

    // 취득일
    private LocalDateTime qualifcationDate;

    // 자격증명
    private String name;

    // 점수
    private int score;

    // 급
    private String level;

}
