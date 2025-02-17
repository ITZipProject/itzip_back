package darkoverload.itzip.feature.csQuiz.service.sub.quizscore;

import darkoverload.itzip.feature.csQuiz.entity.QuizRanking;
import darkoverload.itzip.feature.csQuiz.entity.QuizRankings;
import darkoverload.itzip.feature.csQuiz.entity.QuizScore;
import darkoverload.itzip.feature.csQuiz.repository.quizscore.QuizScoreRepository;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.service.UserService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizScoreServiceImpl implements QuizScoreService {

    private final UserService userService;

    private final QuizScoreRepository quizScoreRepository;

    @Override
    public QuizScore findQuizScoreById(CustomUserDetails userDetails) {
        final UserEntity user = userService.findByEmail(userDetails.getEmail())
                .map(User::convertToEntity)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        return quizScoreRepository.findById(user.getId())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_QUIZ_SCORE));
    }

    @Override
    public QuizRankings getTop6Ranking() {
        final List<QuizScore> scores = quizScoreRepository.findTop6ByOrderByScoreDesc()
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_QUIZ_SCORE_RANKING));

        final List<QuizRanking> rankings = new ArrayList<>();
        int rank = 1;
        for (QuizScore score : scores) {
            rankings.add(QuizRanking.create(rank, score));
            rank++;
        }

        return QuizRankings.of(rankings);
    }

}
