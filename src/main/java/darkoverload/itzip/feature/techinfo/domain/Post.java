package darkoverload.itzip.feature.techinfo.domain;

import darkoverload.itzip.feature.techinfo.controller.post.request.PostCreateRequest;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostBasicResponse;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostBlogPreviewResponse;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostDetailInfoResponse;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostPreviewResponse;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.user.domain.User;

import lombok.*;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 포스트 정보를 나타내는 도메인 클래스.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    /**
     * 블로그 ID.
     */
    private Long blogId;

    /**
     * 포스트의 고유 식별자.
     */
    private String id;

    /**
     * 카테고리 ID.
     */
    private String categoryId;


    /**
     * 포스트 작성일.
     */
    private LocalDateTime createDate;

    /**
     * 포스트 제목.
     */
    private String title;

    /**
     * 포스트 본문.
     */
    private String content;

    /**
     * 썸네일 이미지 경로.
     */
    private String thumbnailImagePath;

    /**
     * 본문에 사용된 이미지 경로 리스트.
     */
    private List<String> contentImagePaths;

    /**
     * 좋아요 개수.
     */
    private Integer likeCount;

    /**
     * 포스트 조회수.
     */
    private Integer viewCount;

    /**
     * 포스트 공개 여부.
     */
    private Boolean isPublic;

    /**
     * 포스트 생성 메서드.
     *
     * @param postCreateDto 포스트 생성 요청 객체
     * @param blogId 블로그 ID
     * @return 생성된 Post 객체
     */
    public static Post createPost(PostCreateRequest postCreateDto, Long blogId) {
        return Post.builder()
                .blogId(blogId)
                .categoryId(postCreateDto.getCategoryId())
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .viewCount(0)
                .likeCount(0)
                .isPublic(true)
                .thumbnailImagePath(postCreateDto.getThumbnailImagePath())
                .contentImagePaths(postCreateDto.getContentImagePaths())
                .build();
    }

    /**
     * Post 도메인 객체를 PostDocument로 변환 (Post ID 제외).
     *
     * @return 변환된 PostDocument 객체
     */
    public PostDocument convertToDocumentWithoutPostId() {
        return PostDocument.builder()
                .blogId(this.blogId)
                .categoryId(new ObjectId(this.categoryId))
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .likeCount(this.likeCount)
                .isPublic(this.isPublic)
                .thumbnailImagePath(this.thumbnailImagePath)
                .contentImagePaths(this.contentImagePaths != null ? this.contentImagePaths : List.of())
                .build();
    }

    /**
     * Post -> PostBlogPreviewResponse 변환 메서드.
     *
     * @return 변환된 PostBlogPreviewResponse 객체
     */
    public PostBlogPreviewResponse convertToBlogPreviewResponse() {
        return PostBlogPreviewResponse.builder()
                .postId(this.id)
                .categoryId(this.categoryId)
                .createDate(this.createDate.toString())
                .title(this.title)
                .content(this.content)
                .likeCount(this.likeCount)
                .thumbnailImagePath(this.thumbnailImagePath)
                .build();
    }

    /**
     * Post -> PostPreviewResponse 변환 메서드.
     *
     * @param user 포스트 작성자 정보
     * @return 변환된 PostPreviewResponse 객체
     */
    public PostPreviewResponse convertToPostPreviewResponse(User user) {
        return PostPreviewResponse.builder()
                .postId(this.id)
                .categoryId(this.categoryId)
                .title(this.title)
                .content(this.content)
                .thumbnailImagePath(this.thumbnailImagePath)
                .likeCount(this.likeCount)
                .profileImagePath(user.getImageUrl())
                .author(user.getNickname())
                .createDate(this.createDate.toString())
                .build();
    }

    /**
     * Post -> PostDetailInfoResponse 변환 메서드.
     *
     * @param user 포스트 작성자 정보
     * @param isLiked 좋아요 여부
     * @return 변환된 PostDetailInfoResponse 객체
     */
    public PostDetailInfoResponse convertToPostDetailInfoResponse(User user, Boolean isLiked) {
        return PostDetailInfoResponse.builder()
                .blogId(this.blogId)
                .email(user.getEmail())
                .postId(this.id)
                .categoryId(this.categoryId)
                .profileImagePath(user.getImageUrl())
                .author(user.getNickname())
                .createDate(this.createDate.toString())
                .title(this.title)
                .content(this.content)
                .thumbnailImagePath(this.thumbnailImagePath)
                .contentImagePaths(this.contentImagePaths)
                .isLiked(isLiked)
                .likeCount(this.likeCount)
                .viewCount(this.viewCount)
                .build();
    }

    /**
     * Post -> PostBasicResponse 변환 메서드.
     *
     * @return 변환된 PostBasicResponse 객체
     */
    public PostBasicResponse convertToPostBasicResponse() {
        return PostBasicResponse.builder()
                .postId(this.id)
                .title(this.title)
                .createDate(this.createDate.toString())
                .build();
    }
}