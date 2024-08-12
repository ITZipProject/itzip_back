package darkoverload.itzip.feature.csQuiz.controller;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizAnswerRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizPointRequest;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizCategoryDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.SortBy;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import darkoverload.itzip.feature.csQuiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//퀴즈들을 사용자가 찾을 수 있도록 하는엔드포인트 모음
@RestController
@RequestMapping("cs-quizzes")
@RequiredArgsConstructor
public class CsQuizzesController {
    private final QuizService quizService;

    /**
     * 주어진 필터와 정렬 기준으로 퀴즈 목록을 조회하는 메서드
     *
     * @param difficulty   퀴즈 난이도 (선택 사항)
     * @param categoryId   카테고리 ID (선택 사항)
     * @param sortBy       정렬 기준 (기본값: NEWEST)
     * @param userId       사용자 ID (기본값: 0)
     * @param inUserSolved 사용자가 푼 퀴즈 포함 여부 (기본값: false)
     * @param page         페이지 번호 (기본값: 0)
     * @param size         페이지 크기 (기본값: 10)
     * @return 필터링되고 정렬된 퀴즈 목록
     */
    @GetMapping("/search")
    public PagedModel<EntityModel<QuizDetailResponse>> getFilteredAndSortedQuizzes(
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "NEWEST") SortBy sortBy,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "false") boolean inUserSolved,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return quizService.QuizzesByDifficultyAndCategoryIdAndUserId(
                difficulty, categoryId, sortBy, userId, inUserSolved, page, size);
    }

    /**
     * 주어진 카테고리 ID에 해당하는 카테고리 정보를 조회하는 메서드
     *
     * @param categoryId 카테고리 ID
     * @return 카테고리 정보
     */
    @GetMapping("/{categoryId}")
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
     * 문제의 정답을 제출하는 엔드포인트
     * @param quizAnswerRequest 문제응 답을 받아오는 객체
     * @return 맞췄으면 "CORRECT", 못맛췄으면 "INCORRECT"를 반환한다
     */
    @PostMapping("/answer")
    public UserQuizStatus submitAnswer(@RequestBody QuizAnswerRequest quizAnswerRequest) {
        return quizService.checkAnswer(quizAnswerRequest);
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
}