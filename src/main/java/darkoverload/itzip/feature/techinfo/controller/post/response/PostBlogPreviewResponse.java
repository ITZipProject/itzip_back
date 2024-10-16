package darkoverload.itzip.feature.techinfo.controller.post.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(
        description = "블로그 포스트 미리보기 응답 객체로, 포스트의 기본 정보를 포함합니다."
)
@Getter
@Builder
@AllArgsConstructor
public class PostBlogPreviewResponse {

    @Schema(description = "포스트 고유 ID", example = "66e724e50000000000db4e53")
    private String postId;

    @Schema(description = "포스트가 속한 카테고리 ID", example = "66ce18d84cb7d0b29ce602f5")
    private String categoryId;

    @Schema(description = "포스트의 제목", example = "밤하늘 아래, 감정의 여정")
    private String title;

    @Schema(
            description = "포스트의 내용 (0 ~ 300자 제한)",
            example = """
                    이 세 개의 이미지는 감정의 복잡한 여정을 시각적으로 표현하고 있다. 
                    첫 번째 장면에서는 소녀가 꿈을 향해 하늘로 비상하려 하지만, 현실의 무게에 의해 아래로 끌려 내려가는 모습을 담고 있다.
                    이는 꿈과 야망을 추구하는 과정에서 마주하는 좌절과 도전을 상징한다. 두 번째 장면에서는 소녀가 밤하늘을 응시하며, 자신의 과거와 잊지 못한 꿈들을 회상하는 장면이 그려진다.
                    창문 밖으로 보이는 거친 파도는 그녀의 내면에서 일어나는 감정의 소용돌이를 나타낸다. 마지막 장면은 비가 내리는 고요한 거리에서 두 사람이 나란히 걷는 모습을 통해, 서
                """
    )
    private String content;

    @Schema(description = "포스트의 좋아요 수", example = "0")
    private Integer likeCount;

    @Schema(description = "포스트의 생성 날짜", example = "2024-09-16T03:18:13.734")
    private String createDate;

    @Schema(
            description = "포스트의 썸네일 이미지 경로",
            example = "https://dy1vg9emkijkn.cloudfront.net/techinfo/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg"
    )
    private String thumbnailImagePath;
}