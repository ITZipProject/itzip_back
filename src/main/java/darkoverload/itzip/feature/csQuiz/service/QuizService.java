package darkoverload.itzip.feature.csQuiz.service;

import darkoverload.itzip.feature.csQuiz.service.sub.quiz.CheckAnswer;
import darkoverload.itzip.feature.csQuiz.service.sub.quiz.CreateQuiz;
import darkoverload.itzip.feature.csQuiz.service.sub.quiz.GivenPointToQuiz;
import darkoverload.itzip.feature.csQuiz.service.sub.quizcategory.FindQuizCategory;
import darkoverload.itzip.feature.csQuiz.service.sub.quizzes.FindQiuzQuery;

//컨트롤러가 사용할 service계층 진입점 상속을 통해서 서브 시스템들을 받아온다.
public interface QuizService extends
        FindQuizCategory, FindQiuzQuery, CheckAnswer, GivenPointToQuiz, CreateQuiz {
}