package darkoverload.itzip.feature.csQuiz.service.Impl.quiz;

import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;

public interface CheckAnswer {
    UserQuizStatus checkAnswer(String quizId, Integer answer, Long userId);
}