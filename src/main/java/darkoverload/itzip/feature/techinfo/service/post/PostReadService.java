package darkoverload.itzip.feature.techinfo.service.post;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.domain.post.PostDetails;
import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import darkoverload.itzip.feature.techinfo.type.SortType;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostReadService {

    Optional<Post> findById(ObjectId id);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findPostsByBlogId(Long blogId, Pageable pageable);

    Page<Post> findPostsByCategoryId(String categoryId, Pageable pageable);

    List<Post> findPostsByDateRange(Long blogId, LocalDateTime creteDate, int limit);

    List<YearlyPostStats> findYearlyPostStatsByBlogId(Long blogId);

    boolean existsById(ObjectId postId);

    PostDetails getPostDetailsById(String postId, CustomUserDetails userDetails);

    Page<Post> getPostsByNickname(String nickname, int page, int size, SortType sortType);

    Page<PostDetails> getAllOrPostsByCategoryId(String categoryId, int page, int size, SortType sortType);

    List<Post> getPostsByDateRange(Long blogId, LocalDateTime creteDate, int limit);

    List<YearlyPostStats> getYearlyPostStatsByBlogId(Long blogId);

    long getPostCount();


}
