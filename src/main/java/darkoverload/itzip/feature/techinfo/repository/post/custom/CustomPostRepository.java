package darkoverload.itzip.feature.techinfo.repository.post.custom;

import darkoverload.itzip.feature.techinfo.dto.post.year.YearlyPostDto;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * PostDocument와 관련된 맞춤형 데이터베이스 작업을 처리하는 인터페이스.
 * 기본 MongoRepository 기능 외에 특정 조건에 따른 포스트 조회나 업데이트 같은 추가 기능을 제공함.
 */
public interface CustomPostRepository {

    /**
     * 주어진 포스트의 카테고리, 제목, 내용, 썸네일 이미지, 본문 이미지 경로를 업데이트
     *
     * @param postId 포스트 ID
     * @param categoryId 카테고리 ID
     * @param title 제목
     * @param content 내용
     * @param thumbnailImagePath 썸네일 이미지 경로
     * @param contentImagePaths 본문 이미지 경로 리스트
     * @return 업데이트 성공 여부
     */
    boolean updatePost(ObjectId postId, ObjectId categoryId, String title, String content, String thumbnailImagePath, List<String> contentImagePaths);

    /**
     * 주어진 포스트의 좋아요 수 업데이트
     *
     * @param postId 포스트 ID
     * @param increment 증가할 좋아요 수
     */
    void updateLikeCount(ObjectId postId, int increment);

    /**
     * 주어진 포스트의 조회수 업데이트
     *
     * @param postId 포스트 ID
     */
    void updateViewCount(ObjectId postId);

    /**
     * 주어진 포스트의 공개 여부를 업데이트
     *
     * @param postId 포스트 ID
     * @param isPublic 공개 여부
     * @return 업데이트 성공 여부
     */
    boolean updatePostVisibility(ObjectId postId, boolean isPublic);

    /**
     * 모든 포스트 목록을 페이지네이션하여 조회
     *
     * @param pageable 페이지네이션 정보
     * @return 페이지네이션된 포스트 목록
     */
    Page<PostDocument> findAllPosts(Pageable pageable);

    /**
     * 주어진 블로그 ID에 해당하는 포스트 목록을 페이지네이션하여 조회
     *
     * @param blogId 블로그 ID
     * @param pageable 페이지네이션 정보
     * @return 페이지네이션된 포스트 목록
     */
    Page<PostDocument> findPostsByBlogId(Long blogId, Pageable pageable);

    /**
     * 주어진 카테고리 ID에 해당하는 포스트 목록을 페이지네이션하여 조회
     *
     * @param categoryId 카테고리 ID
     * @param pageable 페이지네이션 정보
     * @return 페이지네이션된 포스트 목록
     */
    Page<PostDocument> findPostsByCategoryId(ObjectId categoryId, Pageable pageable);

    /**
     * 주어진 블로그 ID와 작성일 기준으로 이전 및 이후의 포스트 목록 조회
     *
     * @param blogId 블로그 ID
     * @param createDate 작성일
     * @param limit 조회할 포스트 수 제한
     * @return 이전 및 이후의 포스트 목록
     */
    List<PostDocument> findAdjacentPosts(Long blogId, LocalDateTime createDate, int limit);

    /**
     * 주어진 블로그 ID에 대한 연간 포스트 데이터 조회
     *
     * @param blogId 블로그 ID
     * @return 연간 포스트 데이터 목록
     */
    List<YearlyPostDto> findYearlyPostCounts(Long blogId);
}