package darkoverload.itzip.feature.csQuiz.service.sub.quiz;

import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;

public interface CheckAnswer {
    UserQuizStatus checkAnswer(String quizId, Integer answer, Long userId);
}