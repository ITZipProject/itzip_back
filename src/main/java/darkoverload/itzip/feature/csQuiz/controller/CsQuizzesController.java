package darkoverload.itzip.feature.csQuiz.controller;

import darkoverload.itzip.feature.csQuiz.entity.SortBy;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import darkoverload.itzip.feature.csQuiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

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
}