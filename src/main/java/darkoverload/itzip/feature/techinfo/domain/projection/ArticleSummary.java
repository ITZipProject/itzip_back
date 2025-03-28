package darkoverload.itzip.feature.techinfo.domain.projection;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public interface ArticleSummary {

    ObjectId getId();

    String getTitle();

    LocalDateTime getCreatedAt();

}
