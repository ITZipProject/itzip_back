package darkoverload.itzip.feature.resume.domain.language;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Language {

    // 어학시험명
    private String name;

    // 급수
    private String level;

    // 점수
    private int score;

    // 취득일
    private LocalDateTime acquistionDate;

}
