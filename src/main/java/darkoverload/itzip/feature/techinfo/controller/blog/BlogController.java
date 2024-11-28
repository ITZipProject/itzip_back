package darkoverload.itzip.feature.techinfo.controller.blog;

import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogDetailsResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogResponse;
import darkoverload.itzip.feature.techinfo.service.blog.BlogReadService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Tech Info Blog",
        description = "기술 정보 블로그 기본 및 상세 정보 조회 기능을 제공하는 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/blog")
public class BlogController {

    private final BlogReadService blogReadService;

    @Operation(
            summary = "블로그 기본 정보 조회",
            description = "지정된 블로그 ID를 사용해 블로그 소개글과 소유자의 프로필 사진, 닉네임, 이메일을 반환합니다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_BLOG})
    @GetMapping("{id}")
    public BlogResponse getBlogBasicInfo(
            @Parameter(description = "블로그 ID", example = "1") @PathVariable @NotNull Long id
    ) {
        return BlogResponse.from(blogReadService.getById(id));
    }

    @Operation(
            summary = "블로그 상세 정보 조회",
            description = "사용자 닉네임으로 블로그의 상세 정보(포스트 목록, 카테고리, 월별 포스트 통계)를 조회합니다."
    )
    @GetMapping("/{nickname}/detail")
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_BLOG})
    public BlogDetailsResponse getBlogDetails(
            @Parameter(description = "사용자 닉네임", example = "hyoseung")
            @PathVariable @NotBlank String nickname
    ) {
        return BlogDetailsResponse.from(blogReadService.getBlogDetailByNickname(nickname));
    }

}
