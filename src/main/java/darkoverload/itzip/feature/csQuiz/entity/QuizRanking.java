package darkoverload.itzip.feature.csQuiz.entity;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class QuizRanking {

    private final int rank;
    private final String name;
    private final int score;

    private QuizRanking(final int rank, final String name, final int score) {
        checkNameNotNull(name);
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    private void checkNameNotNull(final String name) {
        if (Objects.isNull(name)) {
            throw new RestApiException(CommonExceptionCode.QUIZ_PROCESSING_ERROR);
        }
    }

    public static QuizRanking create(final int rank, final QuizScore score) {
        return new QuizRanking(rank, score.getUser().getNickname(), score.getScore());
    }

}
