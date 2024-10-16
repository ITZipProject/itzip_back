package darkoverload.itzip.feature.techinfo.controller.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.service.scrap.ScrapService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

@Tag(
        name = "Tech Info Post Scrap",
        description = "포스트에 대한 스크랩 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/scrap")
public class TechInfoScrapController {

    private final ScrapService scrapService;

    @Operation(
            summary = "스크랩 상태 변경",
            description = "특정 포스트에 대한 스크랩를 추가하거나 취소한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_USER)
    @PostMapping("/toggle")
    public String toggleScrapStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "포스트 ID", example = "66e724e50000000000db4e53") @RequestParam @NotBlank String postId)
    {
        boolean isScrapped = scrapService.toggleScrap(userDetails, postId);
        return isScrapped ? "스크랩이 추가되었습니다." : "스크랩이 취소되었습니다.";
    }
}