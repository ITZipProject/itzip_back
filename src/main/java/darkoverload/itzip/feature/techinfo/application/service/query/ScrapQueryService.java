package darkoverload.itzip.feature.techinfo.application.service.query;

public interface ScrapQueryService {

    boolean existsByUserNicknameAndArticleId(String nickname, String articleId);

}
