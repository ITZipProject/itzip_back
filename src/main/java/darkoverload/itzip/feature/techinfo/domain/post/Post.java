package darkoverload.itzip.feature.techinfo.domain.post;

import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 기술 정보 포스트를 나타내는 도메인 클래스.
 * 게시글 ID, 블로그 ID, 카테고리 ID, 제목, 내용, 조회수, 좋아요 수, 공개 여부, 생성일, 썸네일 이미지 URL 및 본문 이미지 URL 목록을 포함합니다.
 */
@Getter
public class Post {

    private final String id;
    private final Long blogId;
    private final String categoryId;
    private final String title;
    private final String content;
    private final Integer viewCount;
    private final Integer likeCount;
    private final Boolean isPublic;
    private final LocalDateTime createDate;
    private final String thumbnailImagePath;
    private final List<String> contentImagePaths;

    @Builder
    public Post(
            String id,
            Long blogId,
            String categoryId,
            String title,
            String content,
            Integer viewCount,
            Integer likeCount,
            Boolean isPublic,
            LocalDateTime createDate,
            String thumbnailImagePath,
            List<String> contentImagePaths
    ) {
        this.id = id;
        this.blogId = blogId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.isPublic = isPublic;
        this.createDate = createDate;
        this.thumbnailImagePath = thumbnailImagePath;
        this.contentImagePaths = contentImagePaths;
    }

    /**
     * PostCreateRequest 와 블로그 ID로부터 Post 생성합니다.
     *
     * @param request 게시글 생성 요청
     * @param blogId  게시글잎속한 블로그 ID
     * @return Post
     */
    public static Post from(PostCreateRequest request, Long blogId) {
        return Post.builder()
                .blogId(blogId)
                .categoryId(request.categoryId())
                .title(request.title())
                .content(request.content())
                .viewCount(0)
                .likeCount(0)
                .isPublic(true)
                .thumbnailImagePath(request.thumbnailImagePath())
                .contentImagePaths(request.contentImagePaths())
                .build();
    }

}
