package darkoverload.itzip.feature.techinfo.domain.projection;

import darkoverload.itzip.feature.techinfo.domain.entity.ArticleType;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public interface ArticlePreview {

    ObjectId getId();

    long getBlogId();

    ArticleType getType();

    String getTitle();

    String getContent();

    String getThumbnailImageUri();

    long getLikesCount();

    LocalDateTime getCreatedAt();

}
