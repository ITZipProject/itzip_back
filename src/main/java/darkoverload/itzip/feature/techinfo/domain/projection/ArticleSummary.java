package darkoverload.itzip.feature.techinfo.domain.projection;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * 아티클 요약 정보를 제공하는 프로젝션 인터페이스입니다.
 *
 * <p>이 인터페이스는 아티클 목록 등에서 간략한 정보만 필요한 경우 사용됩니다.</p>
 */
public interface ArticleSummary {

    ObjectId getId();

    String getTitle();

    LocalDateTime getCreatedAt();

}
