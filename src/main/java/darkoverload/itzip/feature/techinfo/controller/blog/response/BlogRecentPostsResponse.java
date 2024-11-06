package darkoverload.itzip.feature.techinfo.controller.blog.response;

import darkoverload.itzip.feature.techinfo.controller.post.response.PostBasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(
        description = "블로그에서 특정 게시글의 생성일을 기준으로 이전/이후에 위치한 인접한 게시글 목록을 포함하는 응답 객체"
)
@Getter
@Builder
@AllArgsConstructor
public class BlogRecentPostsResponse {

    @Schema(description = "블로그 회원의 닉네임", example = "hyoseung")
    private String nickname;

    @Schema(description = "특정 게시글를 기준으로 생성일 순서에 따라 최신 및 이전 게시글를 포함한 인접한 게시글 목록을 제공합니다.")
    private List<PostBasicResponse> posts;
}
