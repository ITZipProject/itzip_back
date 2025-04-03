package darkoverload.itzip.feature.techinfo.ui.controller;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.application.service.command.LikeCommandService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Tech Info Like",
        description = "좋아요 등록, 취소 기능을 제공하는 API"
)
@RestController
@RequestMapping("/tech-info")
public class LikeController {

    private final LikeCommandService commandService;

    public LikeController(final LikeCommandService commandService) {
        this.commandService = commandService;
    }

    @Operation(
            summary = "좋아요 등록",
            description = "로그인한 사용자가 특정 아티클에 좋아요를 등록합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.ARTICLE_NOT_FOUND,
            CommonExceptionCode.ALREADY_LIKED_ARTICLE
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @PostMapping("/like/{article_id}")
    public ResponseEntity<String> register(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @Parameter(
                    description = "좋아요를 등록할 아티클의 고유 식별자",
                    required = true,
                    example = "67d2b940d88d2b9236a1faff"
            )
            @NotBlank(message = "아티클 ID는 필수 입니다.")
            @PathVariable(name = "article_id") final String articleId
    ) {
        commandService.create(userDetails, articleId);
        return ResponseEntity.ok("좋아요가 성공적으로 등록되었습니다.");
    }

    @Operation(
            summary = "좋아요 취소",
            description = "로그인한 사용자가 특정 아티클에 대해 좋아요를 취소합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.UNAUTHORIZED,
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.ARTICLE_NOT_FOUND,
    })
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @DeleteMapping("/like/{article_id}")
    public ResponseEntity<String> cancel(
            @Parameter(hidden = true) @AuthenticationPrincipal final CustomUserDetails userDetails,
            @Parameter(
                    description = "취소할 좋아요가 등록된 아티클의 고유 식별자",
                    required = true,
                    example = "67d2b940d88d2b9236a1faff"
            )
            @NotBlank(message = "아티클 ID는 필수 입니다.")
            @PathVariable(name = "article_id") final String articleId
    ) {
        commandService.delete(userDetails, articleId);
        return ResponseEntity.ok("좋아요가 성공적으로 취소되었습니다.");
    }

}
