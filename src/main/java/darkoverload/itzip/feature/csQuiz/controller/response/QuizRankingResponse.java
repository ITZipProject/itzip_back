package darkoverload.itzip.feature.csQuiz.controller.response;

import darkoverload.itzip.feature.csQuiz.entity.QuizRanking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 랭킹 응답 DTO로 사용자의 순위, 이름, 그리고 점수를 포함합니다.")
public class QuizRankingResponse {

    @Schema(description = "사용자의 순위", example = "1")
    private final int rank;

    @Schema(description = "사용자 이름", example = "홍길동")
    private final String name;

    @Schema(description = "퀴즈 점수", example = "100")
    private final int score;

    public QuizRankingResponse(final int rank, final String name, final int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    public static QuizRankingResponse from(final QuizRanking ranking) {
        return new QuizRankingResponse(ranking.getRank(), ranking.getName(), ranking.getScore());
    }

}
