package darkoverload.itzip.feature.csQuiz.service;

import darkoverload.itzip.feature.csQuiz.service.Impl.quizCategory.FindQuizCategory;
import darkoverload.itzip.feature.csQuiz.service.Impl.quizzes.FindQiuzQuery;

//컨트롤러가 사용할 service계층 진입점 상속을 통해서 서브 시스템들을 받아온다.
public interface QuizService extends FindQuizCategory, FindQiuzQuery {
}