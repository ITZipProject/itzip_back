package darkoverload.itzip.feature.csQuiz.service.sub.quizzes;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizQueryRequest;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

//퀴즈 관련 로직을 담아둘 인터페이스
public interface FindQiuzQuery {
    PagedModel<EntityModel<QuizDetailResponse>> findQuizzesByQuery(QuizQueryRequest quizQueryRequest);
}