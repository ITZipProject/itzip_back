package darkoverload.itzip.feature.techinfo.service.post.port;

import darkoverload.itzip.feature.techinfo.domain.post.Post;
import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostReadRepository {

    Optional<Post> findById(ObjectId postId);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findPostsByBlogId(Long blogId, Pageable pageable);

    Page<Post> findPostsByCategoryId(ObjectId categoryId, Pageable pageable);

    List<Post> findPostsByDateRange(Long blogId, LocalDateTime createDate, int limit);

    List<YearlyPostStats> findYearlyPostStatsByBlogId(Long blogId);

    long getPostCount();

    boolean existsById(ObjectId postId);

}
