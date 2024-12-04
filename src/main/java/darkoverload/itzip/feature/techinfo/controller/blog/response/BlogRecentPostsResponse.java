package darkoverload.itzip.feature.techinfo.controller.blog.response;

import darkoverload.itzip.feature.techinfo.controller.post.response.PostResponse;
import darkoverload.itzip.feature.techinfo.domain.blog.BlogPostTimeline;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Schema(
        description = "기술 정보 블로그의 최근 게시글 타임라인 응답"
)
@Builder
public record BlogRecentPostsResponse(
        @Schema(description = "블로그 소유자 닉네임", example = "hyoseung")
        String nickname,

        @Schema(description = "기준 포스트 주변의 최근 포스트 목록")
        List<PostResponse> posts
) {

    public static BlogRecentPostsResponse from(BlogPostTimeline blogPostTimeline) {
        List<PostResponse> postResponses = blogPostTimeline.getPosts().stream()
                .map(PostResponse::from)
                .toList();

        return BlogRecentPostsResponse.builder()
                .nickname(blogPostTimeline.getNickname())
                .posts(postResponses)
                .build();
    }

}
