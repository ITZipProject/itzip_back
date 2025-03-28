package darkoverload.itzip.feature.techinfo.application.event.payload;

import org.bson.types.ObjectId;

public record ArticleHiddenEvent(ObjectId articleId) {
}
