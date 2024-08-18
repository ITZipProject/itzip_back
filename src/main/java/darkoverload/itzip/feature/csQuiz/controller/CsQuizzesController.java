package darkoverload.itzip.feature.csQuiz.controller;

import darkoverload.itzip.feature.csQuiz.entity.SortBy;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import darkoverload.itzip.feature.csQuiz.service.QuizService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;


//퀴즈(복수) 관련 엔드포인트 모음
@Tag(name = "Computer Science Quiz", description = "퀴즈(복수) 관련 엔드포인트 모음")
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
     * @param keyword      검색할 던어
     * @return 필터링되고 정렬된 퀴즈 목록
     */
    @Operation(
            summary = "퀴즈 쿼리 검색",
            description = "여러가지 조건을 통해서 퀴즈를 검색하는 엔드포인트"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_USER})
    @GetMapping("/search")
    public PagedModel<EntityModel<QuizDetailResponse>> getFilteredAndSortedQuizzes(
            @Parameter(description = "퀴즈 난이도 입력칸 1~3") @RequestParam(required = false) Integer difficulty,
            @Parameter(description = "카테고리 식별값 입력칸") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "NEWEST 새로운 순, OLDEST 오래된 순") @RequestParam(required = false, defaultValue = "NEWEST") SortBy sortBy,
            @Parameter(description = "사용자 식별값 입력칸") @RequestParam(required = false) Long userId,
            @Parameter(description = "사용자가 푼 문제를 포함 하는지 true면 포함 false면 미포함") @RequestParam(required = false, defaultValue = "false") boolean inUserSolved,
            @Parameter(description = "문제 페이지 0부터 시작") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "가져올 문제 수") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "검색할 단어") @RequestParam(required = false) String keyword ) {
        return quizService.QuizzesByDifficultyAndCategoryIdAndUserId(
                difficulty, categoryId, sortBy, userId, inUserSolved, page, size, keyword);
    }
}