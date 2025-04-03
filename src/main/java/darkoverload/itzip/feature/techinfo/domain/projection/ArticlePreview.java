package darkoverload.itzip.feature.techinfo.domain.projection;

import darkoverload.itzip.feature.techinfo.domain.entity.ArticleType;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * 아티클이 미리보기 정보를 제공하는 프로젝션 인터페이스입니다.
 *
 * <p>이 인터페이스는 아티클의 상세보기 전 미리보기 용도로 필요한 속성들을 제공합니다.</p>
 */
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
