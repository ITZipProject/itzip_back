package darkoverload.itzip.feature.csQuiz.controller.response;

import darkoverload.itzip.feature.csQuiz.entity.QuizScore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 점수 응답 DTO로 해당 객체는 사용자의 닉네임과 현재 퀴즈 점수를 포함합니다.")
public class QuizScoreResponse {

    @Schema(description = "사용자 닉네임", example = "홍길동")
    private final String name;

    @Schema(description = "사용자의 퀴즈 점수", example = "120")
    private final int score;

    public QuizScoreResponse(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    public static QuizScoreResponse from(final QuizScore score) {
        return new QuizScoreResponse(score.getUser().getNickname(), score.getScore());
    }

}
