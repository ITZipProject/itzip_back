package darkoverload.itzip.feature.csQuiz.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

//사용자가 선택할 정보를 받아올 객체
@Schema(description = "퀴즈 정답을 받아오는 객체 번호로 받는다.")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerRequest {
    @Schema(description = "문제 식별값", required = true, example = "64cbf28d10aebf6d60f7bbae")
    private String quizId;
    @Schema(description = "사용자가 선택한 정답 배열의 index값을 주면 된다.", required = true, example = "0")
    private Integer answer;
}