package darkoverload.itzip.feature.csQuiz.dto.quiz;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

//사용자가 선택할 정보를 받아올 객체
@Schema(description = "퀴즈 정답을 받아오는 객체 번호로 받는다.")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerDto {
    @Schema(description = "문제 식별값", required = true, example = "1a2b3c4d5e6f7g8h9i0j")
    private String id;
    @Schema(description = "사용자가 선택한 정답 배열의 index값을 주면 된다.", required = true, example = "0")
    private Integer answer;
}
