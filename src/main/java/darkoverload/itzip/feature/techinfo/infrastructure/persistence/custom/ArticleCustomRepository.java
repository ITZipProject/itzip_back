package darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom;

import darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.impl.YearlyArticleStatistics;
import org.bson.types.ObjectId;

import java.util.List;

public interface ArticleCustomRepository {

    List<YearlyArticleStatistics> findArticleYearlyStatisticsByBlogId(Long blogId);

    void updateViewCount(ObjectId id, long count);

    void updateLikesCount(ObjectId id, long count);

}
