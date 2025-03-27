package darkoverload.itzip.feature.techinfo.domain.repository;

import darkoverload.itzip.feature.techinfo.domain.entity.Scrap;

import java.util.Optional;

public interface ScrapRepository {

    Scrap save(Scrap like);

    Optional<Scrap> findByUserId(Long userId);

    boolean existsByUser_NicknameAndArticleId(String nickname, String articleId);

    void deleteById(Long id);

    void deleteByUser_NicknameAndArticleId(String nickname, String articleId);

}
