package darkoverload.itzip.feature.techinfo.ui.controller;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.ui.payload.response.BlogResponse;
import darkoverload.itzip.feature.techinfo.application.service.command.BlogCommandService;
import darkoverload.itzip.feature.techinfo.application.service.query.BlogQueryService;
import darkoverload.itzip.feature.techinfo.ui.payload.request.blog.BlogIntroEditRequest;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Tech Info Blog",
        description = "블로그 조회, 변경을 제공하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/blog")
public class BlogController {

    private final BlogCommandService commandService;
    private final BlogQueryService queryService;

    @Operation(
            summary = "블로그 기본 정보 조회",
            description = "지정된 블로그 ID로 블로그 소개글과 소유자의 이메일, 닉네임, 프로필 이미지를 반환합니다."
    )
    @ExceptionCodeAnnotations(CommonExceptionCode.BLOG_NOT_FOUND)
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @GetMapping("/id/{id}")
    public ResponseEntity<BlogResponse> getBlogById(
            @Parameter(
                    description = "조회할 블로그의 고유 식별자",
                    required = true,
                    example = "75"
            )
            @NotBlank(message = "블로그 ID는 필수 입니다.")
            @PathVariable final Long id
    ) {
        final BlogResponse response = queryService.getBlogResponseById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "블로그 기존 정보 조회",
            description = "지정된 닉네임으로 블로그 소개글과 소유자의 이메일, 닉네임, 프로필 이미지를 반환합니다."
    )
    @ExceptionCodeAnnotations(CommonExceptionCode.BLOG_NOT_FOUND)
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<BlogResponse> getBlogByNickname(
            @Parameter(
                    description = "조회할 회원의 닉네임",
                    required = true,
                    example = "빛나는 471번째 곰"
            )
            @NotBlank(message = "회원 닉네임은 필수 입니다.")
            @PathVariable final String nickname
    ) {
        final BlogResponse response = queryService.getBlogResponseByUserNickname(nickname);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "블로그 소개글 변경",
            description = "회원의 블로그 소개글을 변경합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.BLOG_NOT_FOUND
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @PatchMapping("/intro")
    public ResponseEntity<String> modify(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @SwaggerRequestBody(
                    description = "변경할 블로그 소개글을 포함한 요청 Payload",
                    content = @Content(schema = @Schema(implementation = BlogIntroEditRequest.class))
            )
            @RequestBody @Valid final BlogIntroEditRequest request
    ) {
        commandService.updateIntro(userDetails, request);
        return ResponseEntity.ok("소개글이 성공적으로 변경되었습니다.");
    }

}
