package darkoverload.itzip.feature.techinfo.domain.post;

import darkoverload.itzip.feature.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostInfo {

    private final String profileImagePath;
    private final String author;
    private final String postId;
    private final String categoryId;
    private final LocalDateTime createDate;
    private final String title;
    private final String content;
    private final String thumbnailImagePath;
    private final int likeCount;

    @Builder
    public PostInfo (
            String profileImagePath,
            String author,
            String postId,
            String categoryId,
            LocalDateTime createDate,
            String title,
            String content,
            String thumbnailImagePath,
            int likeCount
    ) {
        this.profileImagePath = profileImagePath;
        this.author = author;
        this.postId = postId;
        this.categoryId = categoryId;
        this.createDate = createDate;
        this.title = title;
        this.content = content;
        this.thumbnailImagePath = thumbnailImagePath;
        this.likeCount = likeCount;
    }

    /**
     * Post 와 User 로부터 기본 PostInfo 생성합니다.
     * 좋아요와 스크랩 상태는 포함되지 않습니다.
     *
     * @param post 게시글 정보
     * @param user 작성자 정보
     * @return PostInfo
     */
    public static PostInfo from(Post post, User user) {
        return PostInfo.builder()
                .profileImagePath(user.getImageUrl())
                .author(user.getNickname())
                .postId(post.getId())
                .categoryId(post.getCategoryId())
                .createDate(post.getCreateDate())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnailImagePath(post.getThumbnailImagePath())
                .likeCount(post.getLikeCount())
                .build();
    }

}
