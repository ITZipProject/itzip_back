package darkoverload.itzip.feature.techinfo.service.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.domain.post.PostDetails;
import darkoverload.itzip.feature.techinfo.domain.post.PostInfo;
import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogReadRepository;
import darkoverload.itzip.feature.techinfo.service.like.LikeService;
import darkoverload.itzip.feature.techinfo.service.post.port.PostReadRepository;
import darkoverload.itzip.feature.techinfo.service.scrap.ScrapService;
import darkoverload.itzip.feature.techinfo.type.SortType;
import darkoverload.itzip.feature.techinfo.util.SortUtil;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReadServiceImpl implements PostReadService {

    private final UserRepository userRepository;
    private final BlogReadRepository blogReadRepository;
    private final PostReadRepository postReadRepository;

    private final PostCommandService postCommandService;
    private final ScrapService scrapService;
    private final LikeService likeService;

    /**
     * ID로 포스트를 조회합니다.
     *
     * @param id 포스트 ID
     * @return Optional<Post>
     */
    @Override
    public Optional<Post> findById(ObjectId id) {
        return postReadRepository.findById(id);
    }

    /**
     * 모든 공개 포스트를 페이징하여 조회합니다.
     *
     * @param pageable 페이징 정보
     * @return Page<Post>
     */
    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postReadRepository.findAll(pageable);
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
        return postReadRepository.findPostsByBlogId(blogId, pageable);
    }

    /**
     * 특정 카테고리의 공개 포스트를 페이징하여 조회합니다.
     *
     * @param categoryId 카테고리 ID
     * @param pageable   페이징 정보
     * @return Page<Post>
     */
    @Override
    public Page<Post> findPostsByCategoryId(String categoryId, Pageable pageable) {
        return postReadRepository.findPostsByCategoryId(new ObjectId(categoryId), pageable);
    }

    /**
     * 특정 날짜 범위의 포스트를 조회합니다.
     *
     * @param blogId    블로그 ID
     * @param creteDate 기준 날짜
     * @param limit     조회할 포스트 수
     * @return List<Post>
     */
    @Override
    public List<Post> findPostsByDateRange(Long blogId, LocalDateTime creteDate, int limit) {
        return postReadRepository.findPostsByDateRange(blogId, creteDate, limit);
    }

    /**
     * 특정 블로그의 연간 포스트 통계를 조회합니다.
     *
     * @param blogId 블로그 ID
     * @return List<YearlyPostStats>
     */
    @Override
    public List<YearlyPostStats> findYearlyPostStatsByBlogId(Long blogId) {
        return postReadRepository.findYearlyPostStatsByBlogId(blogId);
    }

    /**
     * 특정 ID의 포스트가 존재하는지 확인합니다.
     *
     * @param postId 포스트 ID
     * @return 존재 여부
     */
    @Override
    public boolean existsById(ObjectId postId) {
        return postReadRepository.existsById(postId);
    }

    /**
     * 포스트 상세 정보를 조회합니다.
     *
     * @param postId      포스트 ID
     * @param userDetails 인증된 사용자 정보 (nullable)
     * @return 포스트 상세 정보
     * @throws RestApiException 사용자 또는 포스트를 찾을 수 없을 때 발생
     */
    @Override
    @Transactional(readOnly = true)
    public PostDetails getPostDetailsById(String postId, CustomUserDetails userDetails) {
        Long loggedInUserId = null;
        boolean isLiked = false;
        boolean isScraped = false;

        if (userDetails != null) {
            loggedInUserId = userRepository.findByEmail(userDetails.getEmail())
                    .map(UserEntity::getId)
                    .orElseThrow(
                            () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                    );

            isLiked = likeService.isLiked(loggedInUserId, postId);
            isScraped = scrapService.isScrapped(loggedInUserId, postId);
        }

        Post post = this.findById(new ObjectId(postId))
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_POST)
                );

        postCommandService.updateFieldWithValue(post.getId(), "view_count", 1);
        Blog blog = blogReadRepository.getById(post.getBlogId());

        return PostDetails.from(post, blog.getUser(), isLiked, isScraped);
    }

    /**
     * 특정 사용자의 포스트를 페이징하여 조회합니다.
     *
     * @param nickname 사용자 닉네임
     * @param page     페이지 번호
     * @param size     페이지 크기
     * @param sortType 정렬 방식
     * @return Page<Post>
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Post> getPostsByNickname(String nickname, int page, int size, SortType sortType) {
        Pageable pageable = PageRequest.of(page, size, SortUtil.getType(sortType));
        Long blogId = blogReadRepository.getByNickname(nickname).getId();
        return this.findPostsByBlogId(blogId, pageable);
    }

    /**
     * 모든 포스트 또는 특정 카테고리의 포스트를 페이징하여 조회합니다.
     *
     * @param categoryId 카테고리 ID (nullable)
     * @param page       페이지 번호
     * @param size       페이지 크기
     * @param sortType   정렬 방식
     * @return Page<PostDetails>
     * @throws RestApiException 포스트를 찾을 수 없을 때 발생
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PostInfo> getAllOrPostsByCategoryId(String categoryId, int page, int size, SortType sortType) {
        Pageable pageable = PageRequest.of(page, size, SortUtil.getType(sortType));

        Page<Post> posts = (categoryId != null) ? this.findPostsByCategoryId(categoryId, pageable) : this.findAll(pageable);

        if (posts.isEmpty()) {
            throw new RestApiException(
                    categoryId != null ? CommonExceptionCode.NOT_FOUND_POST_IN_CATEGORY : CommonExceptionCode.NOT_FOUND_POST
            );
        }

        return posts.map(post -> {
            Blog blog = blogReadRepository.getById(post.getBlogId());
            return PostInfo.from(post, blog.getUser());
        });
    }

    /**
     * 특정 날짜 범위의 포스트를 조회합니다.
     *
     * @param blogId    블로그 ID
     * @param creteDate 기준 날짜
     * @param limit     조회할 포스트 수
     * @return List<Post>
     * @throws RestApiException 포스트를 찾을 수 없을 때 발생
     */
    @Override
    public List<Post> getPostsByDateRange(Long blogId, LocalDateTime creteDate, int limit) {
        List<Post> posts = this.findPostsByDateRange(blogId, creteDate, limit);

        if (posts.isEmpty()) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_POST_IN_BLOG);
        }

        return posts;
    }

    /**
     * 특정 블로그의 연간 포스트 통계를 조회합니다.
     *
     * @param blogId 블로그 ID
     * @return List<YearlyPostStats>
     * @throws RestApiException 포스트를 찾을 수 없을 때 발생
     */
    @Override
    public List<YearlyPostStats> getYearlyPostStatsByBlogId(Long blogId) {
        return this.findYearlyPostStatsByBlogId(blogId);
    }

    /**
     * 전체 공개 포스트 수를 조회합니다.
     *
     * @return 공개 포스트 수
     */
    @Override
    public long getPostCount() {
        return postReadRepository.getPostCount();
    }

}
