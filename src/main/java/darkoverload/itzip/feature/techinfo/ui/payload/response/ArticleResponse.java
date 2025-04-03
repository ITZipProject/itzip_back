package darkoverload.itzip.feature.techinfo.ui.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.domain.projection.ArticlePreview;
import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import darkoverload.itzip.feature.techinfo.domain.projection.ArticleSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "아티클 응답 정보")
@Builder
public record ArticleResponse(
        @Schema(description = "작성자", example = "rowing0328")
        String author,

        @Schema(description = "프로필 이미지 URI", example = "http://example.com/profile.jpg")
        @JsonProperty("profile_image_uri")
        String profileImageUri,

        @Schema(description = "아티클 ID", example = "60a7a28b9a06f913c1f1f7b9")
        String articleId,

        @Schema(description = "블로그 ID", example = "12345")
        long blogId,

        @Schema(description = "아티클 유형", example = "other")
        String type,

        @Schema(description = "아티클 제목", example = "오늘의 뉴스")
        String title,

        @Schema(description = "아티클 내용", example = "이것은 아티클 내용입니다.")
        String content,

        @Schema(description = "썸네일 이미지 URI", example = "http://example.com/thumb.jpg")
        @JsonProperty("thumbnail_image_uri")
        String thumbnailImageUri,

        @Schema(description = "좋아요 수", example = "100")
        @JsonProperty("likes_count")
        Long likesCount,

        @Schema(description = "조회 수", example = "1000")
        @JsonProperty("view_count")
        Long viewCount,

        @Schema(description = "생성 시간", example = "2025-03-13T12:34:56")
        @JsonProperty("created_at")
        String createdAt,

        @Schema(description = "좋아요 여부", example = "true")
        @JsonProperty("is_liked")
        Boolean isLiked,

        @Schema(description = "스크랩 여부", example = "false")
        @JsonProperty("is_scrapped")
        Boolean isScrapped
) {

    public static ArticleResponse from(final Blog blog, final Article article, final boolean isLiked, final boolean isScrapped) {
        return ArticleResponse.builder()
                .author(blog.getUser().getNickname())
                .profileImageUri(blog.getUser().getImageUrl())
                .articleId(article.getId().toHexString())
                .blogId(article.getBlogId())
                .type(article.getType().name().toLowerCase())
                .title(article.getTitle())
                .content(article.getContent())
                .thumbnailImageUri(article.getThumbnailImageUri())
                .likesCount(article.getLikesCount())
                .viewCount(article.getViewCount())
                .createdAt(article.getCreatedAt().toString())
                .isLiked(isLiked)
                .isScrapped(isScrapped)
                .build();
    }

    public static ArticleResponse previewFrom(final Blog blog, final ArticlePreview article) {
        return ArticleResponse.builder()
                .author(blog.getUser().getNickname())
                .profileImageUri(blog.getUser().getImageUrl())
                .articleId(article.getId().toHexString())
                .type(article.getType().name().toLowerCase())
                .title(article.getTitle())
                .content(article.getContent().length() > 300 ? article.getContent().substring(0, 300) : article.getContent())
                .thumbnailImageUri(article.getThumbnailImageUri())
                .likesCount(article.getLikesCount())
                .createdAt(article.getCreatedAt().toString())
                .build();
    }

    public static ArticleResponse previewFrom(final ArticlePreview article) {
        return ArticleResponse.builder()
                .articleId(article.getId().toHexString())
                .type(article.getType().name().toLowerCase())
                .title(article.getTitle())
                .content(article.getContent().length() > 300 ? article.getContent().substring(0, 300) : article.getContent())
                .thumbnailImageUri(article.getThumbnailImageUri())
                .likesCount(article.getLikesCount())
                .createdAt(article.getCreatedAt().toString())
                .build();
    }

    public static ArticleResponse summaryFrom(final ArticleSummary summary) {
        return ArticleResponse.builder()
                .articleId(summary.getId().toHexString())
                .title(summary.getTitle())
                .createdAt(summary.getCreatedAt().toString())
                .build();
    }

}
