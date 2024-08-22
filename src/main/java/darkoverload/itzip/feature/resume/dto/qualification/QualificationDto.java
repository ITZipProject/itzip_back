package darkoverload.itzip.feature.resume.dto.qualification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QualificationDto {

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
