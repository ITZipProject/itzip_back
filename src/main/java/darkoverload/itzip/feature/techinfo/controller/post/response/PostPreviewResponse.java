package darkoverload.itzip.feature.techinfo.controller.post.response;

import darkoverload.itzip.feature.techinfo.domain.post.PostDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(
        description = "기술 정보 포스트 미리보기 응답"
)
@Builder
public record PostPreviewResponse(
        @Schema(description = "포스트 ID", example = "66e724e50000000000db4e53")
        String postId,

        @Schema(description = "카테고리 ID", example = "66ce18d84cb7d0b29ce602f5")
        String categoryId,

        @Schema(description = "제목", example = "밤하늘 아래, 감정의 여정")
        String title,

        @Schema(
                description = "내용 요약 (최대 300자)",
                example = """
                            이 세 개의 이미지는 감정의 복잡한 여정을 시각적으로 표현하고 있다. 
                            첫 번째 장면에서는 소녀가 꿈을 향해 하늘로 비상하려 하지만, 현실의 무게에 의해 아래로 끌려 내려가는 모습을 담고 있다.
                            이는 꿈과 야망을 추구하는 과정에서 마주하는 좌절과 도전을 상징한다. 두 번째 장면에서는 소녀가 밤하늘을 응시하며, 자신의 과거와 잊지 못한 꿈들을 회상하는 장면이 그려진다.
                            창문 밖으로 보이는 거친 파도는 그녀의 내면에서 일어나는 감정의 소용돌이를 나타낸다. 마지막 장면은 비가 내리는 고요한 거리에서 두 사람이 나란히 걷는 모습을 통해, 서
                        """
        )
        String content,

        @Schema(
                description = "썸네일 이미지 URL",
                example = "https://dy1vg9emkijkn.cloudfront.net/techinfo/19cc111f-c8f4-4d64-bd7a-129415e3ffa2.jpg"
        )
        String thumbnailImagePath,

        @Schema(description = "좋아요 수", example = "0")
        Integer likeCount,

        @Schema(description = "작성자 프로필 이미지 URL", example = "")
        String profileImagePath,

        @Schema(description = "작성자 닉네임", example = "hyoseung")
        String author,

        @Schema(description = "작성일", example = "2024-09-16T03:18:13.734")
        String createDate
) {

    public static PostPreviewResponse from(PostDetails postDetails) {
        return PostPreviewResponse.builder()
                .postId(postDetails.getPostId())
                .categoryId(postDetails.getCategoryId())
                .title(postDetails.getTitle())
                .content(postDetails.getContent())
                .thumbnailImagePath(postDetails.getThumbnailImagePath())
                .likeCount(postDetails.getLikeCount())
                .profileImagePath(postDetails.getProfileImagePath())
                .author(postDetails.getAuthor())
                .createDate(postDetails.getCreateDate().toString())
                .build();
    }

}
