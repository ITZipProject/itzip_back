package darkoverload.itzip.feature.techinfo.repository.post.custom;

import darkoverload.itzip.feature.techinfo.dto.post.YearlyPostStats;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostReadRepository {

    Optional<PostDocument> findByPostId(ObjectId id);

    Page<PostDocument> findAll(Pageable pageable);

    Page<PostDocument> findPostsByBlogId(Long blogId, Pageable pageable);

    Page<PostDocument> findPostsByCategoryId(ObjectId categoryId, Pageable pageable);

    List<PostDocument> findPostsByDateRange(Long blogId, LocalDateTime createDate, int limit);

    List<YearlyPostStats> findYearlyPostStatsByBlogId(Long blogId);

}
