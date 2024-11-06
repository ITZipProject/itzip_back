package darkoverload.itzip.feature.csQuiz.service.sub.quiz;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizCreatedRequest;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface CreateQuiz {
    void createQuiz(QuizCreatedRequest quizCreatedRequest, CustomUserDetails customUserDetails);
}
