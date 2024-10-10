package darkoverload.itzip.feature.techinfo.controller.blog;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.controller.blog.request.BlogUpdateRequest;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogBasicInfoResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogDetailInfoResponse;
import darkoverload.itzip.feature.techinfo.service.blog.BlogService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@Tag(
        name = "Tech Info Blog",
        description = "블로그의 기본 정보 조회, 상세 정보 조회, 수정, 삭제 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info")
public class TechInfoBlogController {

    private final BlogService blogService;

    @Operation(
            summary = "블로그 기본 정보 조회",
            description = "지정된 블로그 ID를 사용해 블로그의 제목, 설명, 생성 날짜 등 기본 정보와 소유자의 닉네임을 반환한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_BLOG)
    @GetMapping("/blog/{blogId}/basic/")
    public BlogBasicInfoResponse retrieveBlogSummary(
            @Parameter(description = "블로그 ID", example = "1") @PathVariable @NotNull Long blogId)
    {
        return blogService.getBasicBlogInfo(blogId);
    }

    @Operation(
            summary = "블로그 상세 정보 조회",
            description = "인증된 사용자의 이메일을 사용해 블로그의 상세 정보(포스트 목록, 카테고리 정보)와 월별 포스트 수를 조회한다."
    )
    @GetMapping("/blog/{nickname}/details")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.FORBIDDEN,
            CommonExceptionCode.NOT_FOUND_BLOG
    })
    public BlogDetailInfoResponse fetchBlogDetails(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "사용자 닉네임", example = "hyoseung") @PathVariable @NotBlank String nickname)
    {
        return blogService.getDetailBlogInfo(userDetails, nickname);
    }

    @Operation(
            summary = "블로그 수정",
            description = "사용자가 요청한 내용을 기반으로 블로그 제목, 설명 등의 정보를 업데이트한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_USER,
            CommonExceptionCode.NOT_FOUND_BLOG
    })
    @PostMapping("")
    public String editBlogDetails(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid BlogUpdateRequest request)
    {
        blogService.updateBlog(userDetails, request);
        return "블로그 정보가 수정되었습니다.";
    }

    @Operation(
            summary = "블로그 비활성화 처리",
            description = "지정된 블로그 ID를 사용해 해당 블로그의 공개 상태를 비활성화하고, 사용자에게 보이지 않도록 처리한다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_BLOG)
    @PatchMapping("/{blogId}/hide")
    public String disableBlog(
            @Parameter(description = "블로그 ID", example = "1") @PathVariable @NotNull Long blogId)
    {
        blogService.setBlogHiddenStatus(blogId);
        return "블로그가 비활성화되었습니다.";
    }
}