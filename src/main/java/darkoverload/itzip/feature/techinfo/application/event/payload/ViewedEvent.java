package darkoverload.itzip.feature.techinfo.application.event.payload;

import org.bson.types.ObjectId;

/**
 * 조회 이벤트를 나타내는 레코드입니다.
 *
 * <p>아티클이 조회될 때 발생하며, 조회된 게시글의 식별자를 포함합니다.</p>
 */
public record ViewedEvent(ObjectId articleId) {
}
