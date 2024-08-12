package darkoverload.itzip.feature.csQuiz.service.Impl.quizCategory;

import darkoverload.itzip.feature.csQuiz.controller.response.QuizCategoryDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;

import java.util.List;

//퀴즈 카테고리 관련 로직을 담아둘 인터페이스
public interface FindQuizCategory {
    QuizCategory CategoryById(Long id);
    List<QuizCategoryDetailResponse> AllCategory();
}