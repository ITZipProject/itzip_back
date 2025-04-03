package darkoverload.itzip.feature.techinfo.application.event.payload;

import org.bson.types.ObjectId;

/**
 * 아티클 숨김 이벤트를 나타내는 레코드입니다.
 *
 * <p>아티클이 숨김 처리될 때 발생하며, 숨김 대상 아티클의 식별자를 포함합니다.</p>
 */
public record ArticleHiddenEvent(ObjectId articleId) {
}
