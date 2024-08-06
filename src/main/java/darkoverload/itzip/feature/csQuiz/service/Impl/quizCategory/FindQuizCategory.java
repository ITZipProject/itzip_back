package darkoverload.itzip.feature.csQuiz.service.Impl.quizCategory;

import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;

//퀴즈 카테고리 관련 로직을 담아둘 인터페이스
public interface FindQuizCategory {
    QuizCategory CategoryById(Long id);
}
