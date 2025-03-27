package darkoverload.itzip.feature.techinfo.application.service.command;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.techinfo.ui.payload.request.article.ArticleEditRequest;
import darkoverload.itzip.feature.techinfo.ui.payload.request.article.ArticleRegistrationRequest;
import org.bson.types.ObjectId;

public interface ArticleCommandService {

    String create(CustomUserDetails userDetails, ArticleRegistrationRequest request);

    void update(CustomUserDetails userDetails, ArticleEditRequest request);

    void delete(CustomUserDetails userDetails, String articleId);

    void updateViewCount(ObjectId id, long count);

    void updateLikesCount(String id, long count);

}
