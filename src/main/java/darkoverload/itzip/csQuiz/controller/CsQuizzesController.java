package darkoverload.itzip.csQuiz.controller;

import darkoverload.itzip.csQuiz.dto.SortBy;
import darkoverload.itzip.csQuiz.dto.quiz.QuizDetailDto;
import darkoverload.itzip.csQuiz.entity.QuizCategory;
import darkoverload.itzip.csQuiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
     * @param difficulty 퀴즈 난이도 (선택 사항)
     * @param categoryId 카테고리 ID (선택 사항)
     * @param sortBy 정렬 기준 (기본값: NEWEST)
     * @param userId 사용자 ID (기본값: 0)
     * @param inUserSolved 사용자가 푼 퀴즈 포함 여부 (기본값: false)
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 필터링되고 정렬된 퀴즈 목록
     */
    @GetMapping("/search")
    public Page<QuizDetailDto> getFileredAndSortedQuizzes(
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "NEWEST")SortBy sortBy,
            @RequestParam(required = false, defaultValue = "0") Long userId,
            @RequestParam(required = false, defaultValue = "false") boolean inUserSolved,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        return quizService.QuizzesByDifficultyAndCategoryIdAndUserId(difficulty, categoryId,
                sortBy, userId, inUserSolved, page, size);
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
}
