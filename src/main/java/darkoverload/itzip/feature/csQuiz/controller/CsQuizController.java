package darkoverload.itzip.feature.csQuiz.controller;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizAnswerRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizPointRequest;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizCategoryDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import darkoverload.itzip.feature.csQuiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//퀴즈(단수) 관련 엔드포인트
@RestController
@RequestMapping("cs-quiz")
@RequiredArgsConstructor
public class CsQuizController {
    private final QuizService quizService;

    /**
     * 주어진 카테고리 ID에 해당하는 카테고리 정보를 조회하는 메서드
     *
     * @param categoryId 카테고리 ID
     * @return 카테고리 정보
     */
    @GetMapping("/category/{categoryId}")
    public QuizCategory getCategories(@PathVariable Long categoryId){
        return quizService.CategoryById(categoryId);
    }

    /**
     * 모든 카테고리를 조회하는 엔드포인트
     *
     * @return 모든 카테고리 response 리스트
     */
    @GetMapping("/categories")
    public List<QuizCategoryDetailResponse> getAllCategories(){
        return quizService.AllCategory();
    }

    /**
     * 푼 문제에 점수를 줄때 사용하는 엔드포인트
     * @param quizPointRequest 푼 문제에 점수를 줄때 사용할 객체
     * @return 변한 문제의 점수 값
     */
    @PostMapping("/point")
    public Integer sumPointToQuiz(@RequestBody QuizPointRequest quizPointRequest) {
        return quizService.givenPointToQuiz(quizPointRequest);
    }

    /**
     * 문제의 정답을 제출하는 엔드포인트
     * @param quizAnswerRequest 문제응 답을 받아오는 객체
     * @return 맞췄으면 "CORRECT", 못맛췄으면 "INCORRECT"를 반환한다
     */
    @PostMapping("/answer")
    public UserQuizStatus submitAnswer(@RequestBody QuizAnswerRequest quizAnswerRequest) {
        return quizService.checkAnswer(quizAnswerRequest);
    }
}
