package darkoverload.itzip.feature.techinfo.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(
        description = "블로그에서 특정 포스트의 생성일을 기준으로 이전/다음에 위치한 인접한 포스트 목록을 포함하는 응답 객체"
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogAdjacentPostsResponse {

    @Schema(description = "블로그 회원의 닉네임", example = "hyoseung")
    private String nickname;  // 블로그 회원 닉네임

    @Schema(description = "특정 포스트를 기준으로 생성일 순서에 따라 최신 및 이전 포스트를 포함한 인접한 포스트 목록을 제공합니다.")
    private List<PostBasicResponse> posts;  // 생성일 기준 인접한 포스트 목록
}
