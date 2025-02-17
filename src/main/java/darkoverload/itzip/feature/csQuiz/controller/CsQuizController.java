package darkoverload.itzip.feature.csQuiz.controller;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizAnswerRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizCreatedRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizPointRequest;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizCategoryDetailResponse;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizRankingResponse;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizRankingResponses;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizScoreResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import darkoverload.itzip.feature.csQuiz.service.QuizService;
import darkoverload.itzip.feature.csQuiz.service.sub.quizscore.QuizScoreService;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import darkoverload.itzip.global.config.swagger.SwaggerRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//퀴즈(단수) 관련 엔드포인트 모음
@Tag(name = "Computer Science Quizzes", description = "퀴즈(단수) 관련 엔드포인트 모음 및 카테고리")
@RestController
@RequestMapping("cs-quiz")
@RequiredArgsConstructor
public class CsQuizController {

    private final QuizService quizService;
    private final QuizScoreService quizScoreService;

    /**
     * 주어진 카테고리 ID에 해당하는 카테고리 정보를 조회하는 메서드
     *
     * @param categoryId 카테고리 ID
     * @return 카테고리 정보
     */
    @Operation(
            summary = "카테고리 ID로 조회",
            description = "주어진 카테고리 ID에 해당하는 카테고리 정보를 조회하는 메서드"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_CATEGORY)
    @GetMapping("/category/{categoryId}")
    public QuizCategory getCategories(@Parameter(description = "카테고리 ID", required = true) @PathVariable Long categoryId){
        return quizService.findCategoryById(categoryId);
    }

    /**
     * 모든 카테고리를 조회하는 엔드포인트
     *
     * @return 모든 카테고리 response 리스트
     */
    @Operation(
            summary = "모든 카테고리 조회",
            description = "모든 카테고리를 조회하는 엔드포인트"
    )
    @GetMapping("/categories")
    public List<QuizCategoryDetailResponse> getAllCategories(){
        return quizService.findAllCategory();
    }


    /**
     * 푼 문제에 점수를 줄때 사용하는 엔드포인트
     * @param quizPointRequest 푼 문제에 점수를 줄때 사용할 객체
     * @return 변한 문제의 점수 값
     */
    @Operation(summary = "점수 부여", description = "푼 문제에 점수를 부여하는 엔드포인트")
    @PostMapping("/point")
    public Integer sumPointToQuiz(
            @SwaggerRequestBody(description = "점수를 부여할 문제의 정보와 점수", content = @Content(schema = @Schema(implementation = QuizPointRequest.class)))
            @RequestBody QuizPointRequest quizPointRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return quizService.givenPointToQuiz(quizPointRequest, customUserDetails);
    }

    /**
     * 문제의 정답을 제출하는 엔드포인트
     * @param quizAnswerRequest 문제응 답을 받아오는 객체
     * @return 맞췄으면 "CORRECT", 못맛췄으면 "INCORRECT"를 반환한다
     */
    @Operation(
            summary = "문제 정답 제출",
            description = "문제의 정답을 제출하는 엔드포인트"
    )
    @PostMapping("/answer")
    public UserQuizStatus submitAnswer(
            @SwaggerRequestBody(description = "제출할 문제의 정답 정보", required = true, content = @Content(
                    schema = @Schema(implementation = QuizAnswerRequest.class)
            )) @RequestBody QuizAnswerRequest quizAnswerRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return quizService.checkAnswer(quizAnswerRequest, customUserDetails);
    }

    /**
     * 문제를 생성할 때 사용하는 엔드포인트
     * @param quizCreatedRequest 문제를 생설할 때 사용하는 객체
     * @return
     */
    @Operation(
            summary = "문제 생성",
            description = "문제를 생성할 때 사용하는 엔드포인트"
    )
    @PostMapping("/")
    public String createQuiz(
            @SwaggerRequestBody(description = "생성할 문제에 대한 정보", required = true, content = @Content(
                    schema = @Schema(implementation = QuizCreatedRequest.class)
            )) @RequestBody QuizCreatedRequest quizCreatedRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        quizService.createQuiz(quizCreatedRequest, customUserDetails);
        return "문제를 생성했습니다.";
    }

    @Operation(
            summary = "퀴즈 점수 조회",
            description = "현재 인증된 사용자의 퀴즈 점수를 조회하여 반환합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_QUIZ_SCORE
    })
    @GetMapping("/score")
    public QuizScoreResponse getScore(@AuthenticationPrincipal final CustomUserDetails customUserDetails) {
        return QuizScoreResponse.from(
                quizScoreService.findQuizScoreById(customUserDetails)
        );
    }

    @Operation(
            summary = "퀴즈 랭킹 조회",
            description = "퀴즈 점수를 기준으로 상위 6명의 랭킹 정보를 조회하여 반환합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_QUIZ_SCORE_RANKING,
            CommonExceptionCode.QUIZ_PROCESSING_ERROR
    })
    @GetMapping("/rankings")
    public QuizRankingResponses getRankings() {
        return QuizRankingResponses.of(
                quizScoreService.getTop6Ranking().getRankings().stream()
                        .map(QuizRankingResponse::from)
                        .toList()
        );
    }

}
