package darkoverload.itzip.feature.csQuiz.service.sub.quiz;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizPointRequest;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;

public interface GivenPointToQuiz {
    Integer givenPointToQuiz(QuizPointRequest quizPointRequest, CustomUserDetails customUserDetails);
}
