package darkoverload.itzip.feature.techinfo.application.service.query;

public interface LikeQueryService {

    boolean existsByUserNicknameAndArticleId(String nickname, String articleId);

}
