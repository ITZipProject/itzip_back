package darkoverload.itzip.feature.csQuiz.service.sub.quiz;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizAnswerRequest;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface CheckAnswer {
    UserQuizStatus checkAnswer(QuizAnswerRequest quizAnswerRequest, CustomUserDetails customUserDetails);
}