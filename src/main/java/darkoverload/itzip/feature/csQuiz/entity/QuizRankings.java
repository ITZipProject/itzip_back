package darkoverload.itzip.feature.csQuiz.entity;

import java.util.Collections;
import java.util.List;

public class QuizRankings {

    private final List<QuizRanking> rankings;

    private QuizRankings(final List<QuizRanking> rankings) {
        this.rankings = Collections.unmodifiableList(rankings);
    }

    public static QuizRankings of(final List<QuizRanking> rankings) {
        return new QuizRankings(rankings);
    }

    public List<QuizRanking> getRankings() {
        return rankings;
    }

}
