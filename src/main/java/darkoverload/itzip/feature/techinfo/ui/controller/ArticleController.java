package darkoverload.itzip.feature.techinfo.ui.controller;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.generator.PagedModelGenerator;
import darkoverload.itzip.feature.techinfo.ui.payload.response.ArticleResponse;
import darkoverload.itzip.feature.techinfo.application.service.command.ArticleCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.ArticleQueryService;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.impl.YearlyArticleStatistics;
import darkoverload.itzip.feature.techinfo.ui.payload.request.article.ArticleEditRequest;
import darkoverload.itzip.feature.techinfo.ui.payload.request.article.ArticleRegistrationRequest;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import darkoverload.itzip.global.config.swagger.SwaggerRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "Tech Info Article",
        description = "아티클 조회, 등록, 변경, 취소 기능을 제공하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class ArticleController {

    private final ArticleCommandService commandService;
    private final ArticleQueryService queryService;

    @Operation(
            summary = "아티클 상세 조회",
            description = "특정 아티클의 상세 정보를 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.ARTICLE_NOT_FOUND)
    @GetMapping("/article/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @Parameter(
                    description = "조회할 아티클의 고유 식별자",
                    required = true,
                    example = "67d2b940d88d2b9236a1faff"
            )
            @NotBlank(message = "아티클 ID는 필수 입니다.")
            @PathVariable("id") final String id
    ) {
        final ArticleResponse response = queryService.getArticleById(userDetails, id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "카테고리 타입별 아티클 미리보기 목록 조회",
            description = "카테고리별 필터링 및 정렬된 아티클 미리보기 목록을 페이징하여 반환합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.ARTICLE_NOT_FOUND)
    @GetMapping("/articles/preview")
    public ResponseEntity<PagedModel<EntityModel<ArticleResponse>>> getArticlePreviews(
            @Parameter(
                    description = "필터링할 아티클 타입 (예: other, tech_ai 등)"
            )
            @RequestParam(name = "article_type", required = false) final String articleType,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "12") final int size,
            @Parameter(
                    description = "정렬 기준 (예: newest, oldest, view_count, like_count)",
                    example = "newest"
            )
            @RequestParam(name = "sort_type", defaultValue = "newest") final String sortType
    ) {
        final Page<ArticleResponse> responses = queryService.getArticlesPreviewByType(articleType, page, size, sortType);
        final PagedModel<EntityModel<ArticleResponse>> pagedModel = PagedModelGenerator.generate(responses);
        return ResponseEntity.ok(pagedModel);
    }

    @Operation(
            summary = "사용자 닉네임별 아티클 미리보기 목록 조회",
            description = "지정된 닉네임으로 정렬된 아티클 미리보기 목록을 페이징하여 반환합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.ARTICLE_NOT_FOUND)
    @GetMapping("/author/{nickname}/articles/preview")
    public ResponseEntity<PagedModel<EntityModel<ArticleResponse>>> getArticlePreviewsByNickname(
        @Parameter(
                description = "조회할 사용자의 닉네임",
                required = true,
                example = "빛나는 471번째 곰"
        )
        @PathVariable final String nickname,
        @RequestParam(defaultValue = "0") final int page,
        @RequestParam(defaultValue = "10") final int size,
        @Parameter(
                description = "정렬 기준 (예: newest, oldest, view_count, like_count)",
                example = "newest"
        )
        @RequestParam(name = "sort_type", defaultValue = "newest") final String sortType
    ) {
        final Page<ArticleResponse> responses = queryService.getArticlesPreviewByAuthor(nickname, page, size, sortType);
        final PagedModel<EntityModel<ArticleResponse>> pagedModel = PagedModelGenerator.generate(responses);
        return ResponseEntity.ok(pagedModel);
    }

    @Operation(
            summary = "블로그 년간 아티클 통계 조회",
            description = "주어진 블로그 ID로 연도별 아티클 통계 정보를 반환합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @GetMapping("/articles/{blog_id}/stats")
    public ResponseEntity<List<YearlyArticleStatistics>> getYearlyArticleStatistics(
            @Parameter(
                    description = "통계를 조회할 대상 블로그 ID",
                    required = true,
                    example = "75"
            )
            @NotNull(message = "블로그 ID는 필수 입니다.")
            @PathVariable("blog_id") final long blogId
    ) {
        final List<YearlyArticleStatistics> articleStatistics = queryService.getYearlyArticleStatisticsByBlogId(blogId);
        return ResponseEntity.ok(articleStatistics);
    }

    @Operation(
            summary = "인접한 아티클 조회",
            description = "주어진 블로그 ID, 아티클 타입, 등록일 기준으로 이후, 이전 아티클 목록을 반환합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @GetMapping("/articles/adjacent")
    public ResponseEntity<List<ArticleResponse>> getAdjacentArticles(
            @Parameter(
                    description = "인접한 아티클을 조회할 대상 블로그 ID",
                    required = true,
                    example = "75"
            )
            @NotNull(message = "블로그 ID는 필수 입니다.")
            @RequestParam final Long blogId,
            @Parameter(
                    description = "조회할 아티클의 타입",
                    required = true,
                    example = "other"
            )
            @NotBlank(message = "아티클 타입은 필수 입니다.")
            @RequestParam final String articleType,
            @Parameter(
                    description = "조회 기준이 되는 아티클의 등록일",
                    required = true,
                    example = "2025-03-04T00:00:00"
            )
            @NotBlank(message = "등록일은 필수 입니다.")
            @RequestParam final LocalDateTime createdAt
    ) {
        final List<ArticleResponse> articleResponses = queryService.getAdjacentArticles(blogId, articleType, createdAt);
        return ResponseEntity.ok(articleResponses);
    }

    @Operation(
            summary = "아티클 등록",
            description = "새로운 아티클을 등록합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.BLOG_NOT_FOUND
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @PostMapping("/article")
    public ResponseEntity<Void> register(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @SwaggerRequestBody(
                    description = "등록할 아티클의 정보를 담은 요청 Payload",
                    content = @Content(schema = @Schema(implementation = ArticleRegistrationRequest.class))
            )
            @RequestBody final ArticleRegistrationRequest request
    ) {
        final String response = commandService.create(userDetails, request);
        final URI location = URI.create(String.format("/tech-info/article/%s", response));
        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "아티클 변경",
            description = "주어진 요청을 기반으로 아티클을 변경합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.BLOG_NOT_FOUND,
            CommonExceptionCode.ARTICLE_NOT_FOUND
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @PutMapping("/article")
    public String modify(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @SwaggerRequestBody(
                    description = "변경할 아티클의 정보를 담은 요청 Payload",
                    content = @Content(schema = @Schema(implementation = ArticleEditRequest.class))
            )
            @RequestBody final ArticleEditRequest request
    ) {
        commandService.update(userDetails, request);
        return "아티클이 성공적으로 변경되었습니다.";
    }

    @Operation(
            summary = "아티클 등록 취소",
            description = "아티클을 비공개 상태로 전환합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.BLOG_NOT_FOUND,
            CommonExceptionCode.ARTICLE_NOT_FOUND
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @DeleteMapping("/article/{id}")
    public String cancel(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @Parameter(
                    description = "취소할 대상 아티클 ID",
                    required = true,
                    example = "67d2b940d88d2b9236a1faff"
            )
            @NotBlank(message = "아티클 ID는 필수 입니다.")
            @PathVariable(name = "id") final String articleId
    ) {
        commandService.delete(userDetails, articleId);
        return "아티클이 성공적으로 삭제되었습니다.";
    }

}
