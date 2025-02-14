package darkoverload.itzip.feature.csQuiz.service.sub.quizscore;

import darkoverload.itzip.feature.csQuiz.entity.QuizRankings;
import darkoverload.itzip.feature.csQuiz.entity.QuizScore;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface QuizScoreService {

    QuizScore findQuizScoreById(CustomUserDetails userDetails);

    QuizRankings getTop6Ranking();

}
