package darkoverload.itzip.feature.techinfo.repository.post;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.service.post.port.PostReadRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * MongoDB를 사용하여 포스트 조회 작업을 수행하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class PostReadRepositoryImpl implements PostReadRepository {

    private final MongoPostReadRepository repository;

    /**
     * ID로 포스트를 조회합니다.
     *
     * @param id 포스트 ID
     * @return Optional<Post>
     */
    @Override
    public Optional<Post> findById(ObjectId id) {
        return repository.findByPostId(id).map(PostDocument::toModel);
    }

    /**
     * 모든 공개 포스트를 페이징하여 조회합니다.
     *
     * @param pageable 페이징 정보
     * @return Page<Post>
     */
    @Override
    public Page<Post> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(PostDocument::toModel);
    }

    /**
     * 특정 블로그의 공개 포스트를 페이징하여 조회합니다.
     *
     * @param blogId   블로그 ID
     * @param pageable 페이징 정보
     * @return Page<Post>
     */
    @Override
    public Page<Post> findPostsByBlogId(Long blogId, Pageable pageable) {
        return repository.findPostsByBlogId(blogId, pageable).map(PostDocument::toModel);
    }

    /**
     * 특정 카테고리의 공개 포스트를 페이징하여 조회합니다.
     *
     * @param categoryId 카테고리 ID
     * @param pageable   페이징 정보
     * @return Page<Post>
     */
    @Override
    public Page<Post> findPostsByCategoryId(ObjectId categoryId, Pageable pageable) {
        return repository.findPostsByCategoryId(categoryId, pageable).map(PostDocument::toModel);
    }

    /**
     * 특정 날짜 범위의 포스트를 조회합니다.
     *
     * @param blogId     블로그 ID
     * @param createDate 기준 날짜
     * @param limit      조회할 포스트 수
     * @return List<Post>
     */
    @Override
    public List<Post> findPostsByDateRange(Long blogId, LocalDateTime createDate, int limit) {
        return repository.findPostsByDateRange(blogId, createDate, limit).stream().map(PostDocument::toModel).toList();
    }

    /**
     * 특정 블로그의 연간 포스트 통계를 조회합니다.
     *
     * @param blogId 블로그 ID
     * @return List<YearlyPostStats>
     */
    @Override
    public List<YearlyPostStats> findYearlyPostStatsByBlogId(Long blogId) {
        return repository.findYearlyPostStatsByBlogId(blogId);
    }

    /**
     * 전체 공개 포스트 수를 조회합니다.
     *
     * @return 공개 포스트 수
     */
    @Override
    public long getPostCount() {
        return repository.countByIsPublicTrue();
    }

    /**
     * 특정 ID의 공개 포스트가 존재하는지 확인합니다.
     *
     * @param postId 포스트 ID
     * @return 존재 여부
     */
    @Override
    public boolean existsById(ObjectId postId) {
        return repository.existsByIdAndIsPublicTrue(postId);
    }

}
