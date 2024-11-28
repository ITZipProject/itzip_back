package darkoverload.itzip.feature.techinfo.controller.blog;

import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogPostPreviewResponse;
import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogRecentPostsResponse;
import darkoverload.itzip.feature.techinfo.service.blog.BlogReadService;
import darkoverload.itzip.feature.techinfo.service.post.PostReadService;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.PagedModelUtil;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(
        name = "Tech Info Blog",
        description = "기술 정보 블로그 포스트 미리보기 및 조회 기능"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-info/blog/posts")
public class BlogPostController {

    private final BlogReadService blogReadService;
    private final PostReadService postReadService;

    @Operation(
            summary = "블로그의 최근 인접 포스트 조회",
            description = "주어진 블로그 ID와 특정 생성 날짜를 사용하여 해당 블로그의 인접한 포스트 목록을 조회한다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_BLOG})
    @GetMapping("/recent")
    public BlogRecentPostsResponse getBlogRecentPosts(
            @Parameter(description = "블로그 ID", example = "1") @RequestParam(value = "blogId") @NotNull Long blogId,
            @Parameter(description = "생성 날짜", example = "2024-09-16T03:18:13.734") @RequestParam("createDate") @NotNull LocalDateTime createDate
    ) {
        return BlogRecentPostsResponse.from(blogReadService.getBlogRecentPostsByIdAndCreateDate(blogId, createDate));
    }

    @Operation(
            summary = "블로그 포스트 미리보기 조회",
            description = "지정된 블로그 ID를 사용해 포스트의 미리보기 정보를 반환한다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_BLOG})
    @GetMapping("/{nickname}/preview")
    public PagedModel<EntityModel<BlogPostPreviewResponse>> getBlogPostPreviews(
            @Parameter(description = "닉네임", example = "hyoseung") @PathVariable @NotBlank String nickname,
            @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST") SortType sortType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size
    ) {
        Page<BlogPostPreviewResponse> blogPostPreviewResponsePage = postReadService.getPostsByNickname(nickname, page,
                        size, sortType)
                .map(BlogPostPreviewResponse::from);

        return PagedModelUtil.create(blogPostPreviewResponsePage);
    }

}
