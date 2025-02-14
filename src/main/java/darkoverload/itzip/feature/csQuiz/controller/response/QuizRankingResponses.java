package darkoverload.itzip.feature.csQuiz.controller.response;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class QuizRankingResponses {

    private final List<QuizRankingResponse> responses;

    private QuizRankingResponses(final List<QuizRankingResponse> responses) {
        this.responses = Collections.unmodifiableList(responses);
    }

    public static QuizRankingResponses of(final List<QuizRankingResponse> responses) {
        return new QuizRankingResponses(responses);
    }

}
