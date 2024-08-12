package darkoverload.itzip.feature.csQuiz.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "퀴즈 평가 점수를 받아올 객체")
@Getter
@Builder
public class QuizPointRequest {
    @Schema(description = "평가 점수를 주는 사용자 Id", example = "5")
    private Long userId;

    @Schema(description = "평가 점수를 받는 문제 Id", example = "64cbf28d10aebf6d60f7bbae")
    private String quizId;

    @Schema(description = "사용자가 문제에 준 점수", example = "2")
    private Integer points;
}