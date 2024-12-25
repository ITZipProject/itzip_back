package darkoverload.itzip.feature.techinfo.domain.post;

import darkoverload.itzip.feature.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 기술 정보 게시글의 상세 정보를 나타내는 도메인 클래스.
 * 작성자 정보, 게시글 정보, 좋아요 및 스크랩 상태 등을 포함합니다.
 */
@Getter
public class PostDetails {

    private final String profileImagePath;
    private final String author;
    private final String email;
    private final Long blogId;
    private final String postId;
    private final String categoryId;
    private final LocalDateTime createDate;
    private final String title;
    private final String content;
    private final int viewCount;
    private final int likeCount;
    private final String thumbnailImagePath;
    private final List<String> contentImagePaths;
    private final boolean isLiked;
    private final boolean isScrapped;

    @Builder
    public PostDetails(
            String profileImagePath,
            String author,
            String email,
            Long blogId,
            String postId,
            String categoryId,
            LocalDateTime createDate,
            String title,
            String content,
            int likeCount,
            int viewCount,
            String thumbnailImagePath,
            List<String> contentImagePaths,
            boolean isLiked,
            boolean isScrapped
    ) {
        this.profileImagePath = profileImagePath;
        this.author = author;
        this.email = email;
        this.blogId = blogId;
        this.postId = postId;
        this.categoryId = categoryId;
        this.createDate = createDate;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.thumbnailImagePath = thumbnailImagePath;
        this.contentImagePaths = contentImagePaths;
        this.isLiked = isLiked;
        this.isScrapped = isScrapped;

    }

    /**
     * Post, User 와 좋아요, 스크랩 상태로부터 PostDetails 생성합니다.
     *
     * @param post     게시글 정보
     * @param user     작성자 정보
     * @param liked    좋아요 상태
     * @param scrapped 스크랩 상태
     * @return PostDetails
     */
    public static PostDetails from(Post post, User user, boolean liked, boolean scrapped) {
        return PostDetails.builder()
                .profileImagePath(user.getImageUrl())
                .author(user.getNickname())
                .email(user.getEmail())
                .blogId(post.getBlogId())
                .postId(post.getId())
                .categoryId(post.getCategoryId())
                .createDate(post.getCreateDate())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .thumbnailImagePath(post.getThumbnailImagePath())
                .contentImagePaths(post.getContentImagePaths())
                .isLiked(liked)
                .isScrapped(scrapped)
                .build();
    }

}
