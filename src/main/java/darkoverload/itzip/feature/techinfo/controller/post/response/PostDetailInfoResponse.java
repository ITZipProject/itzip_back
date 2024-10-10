package darkoverload.itzip.feature.techinfo.controller.post.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(
        description = "포스트 상세 정보를 반환하는 응답 객체로, 포스트의 모든 세부 정보와 상태를 포함합니다."
)
@Getter
@Builder
@AllArgsConstructor
public class PostDetailInfoResponse {

    @Schema(description = "블로그 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "작성자 고유 Email", example = "dev.hyoseung@gmail.com")
    private String email;

    @Schema(description = "포스트 고유 ID", example = "66e9a2ed666b0728ada7edbf")
    private String postId;

    @Schema(description = "포스트가 속한 카테고리 ID", example = "66ce18d84cb7d0b29ce602f5")
    private String categoryId;

     @Schema(description = "작성자 프로필 이미지 경로", example = "")
     private String profileImagePath;

    @Schema(description = "포스트 작성자의 닉네임", example = "hyoseung")
    private String author;

    @Schema(description = "포스트 생성일", example = "2024-09-16T03:18:13.734")
    private String createDate;

    @Schema(description = "포스트 제목", example = "밤하늘 아래, 감정의 여정")
    private String title;

    @Schema(
            description = "포스트 본문 내용",
            example = """
                세 개의 이미지는 감정의 여정을 표현한다. 첫 번째 장면에서는 소녀가 자유를 꿈꾸며 하늘로 날아오르지만 현실의 무게에 의해 추락하는 모습을 담고 있다. 
                이는 인간이 꿈을 꾸고 좌절을 마주하는 과정을 상징한다. 두 번째 장면에서는 소녀가 밤하늘을 바라보며 과거의 기억과 꿈을 되새긴다. 창문 너머로 보이는 파도는 그녀의 내면의 감정을 표현한다. 
                마지막 장면은 비 내리는 거리에서 두 사람이 함께 걸어가는 모습으로, 서로에게 의지하는 관계를 보여준다. 빗소리와 고요한 풍경은 그들의 감정을 더욱 부각시킨다.
                이 이미지는 꿈, 좌절, 고독, 그리고 위안을 주제로 하여 인간이 감정을 마주하고 성장하는 과정을 담아냈다.
                """
    )
    private String content;

    @Schema(
            description = "포스트의 썸네일 이미지 경로",
            example = "https://dy1vg9emkijkn.cloudfront.net/techinfo/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg"
    )
    private String thumbnailImagePath;

    @Schema(
            description = "포스트 본문 이미지 경로 리스트",
            example = """
                  [
                    "https://dy1vg9emkijkn.cloudfront.net/techinfo/7635bb80-416a-4042-a901-552df46351a8.png",
                    "https://dy1vg9emkijkn.cloudfront.net/techinfo/50d081ca-b2f5-4162-926f-0f061aec2554.png"
                  ]
                  """
    )
    private List<String> contentImagePaths;

    @Schema(description = "좋아요 상태 여부", example = "false")
    private Boolean isLiked;

    @Schema(description = "포스트 좋아요 수", example = "0")
    private Integer likeCount;

    @Schema(description = "포스트 조회수", example = "4")
    private Integer viewCount;
}