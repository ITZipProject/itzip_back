package darkoverload.itzip.feature.techinfo.domain.repository;

import darkoverload.itzip.feature.techinfo.domain.entity.Like;

import java.util.Optional;

public interface LikeRepository {

    Like save(Like like);

    Optional<Like> findByUserId(Long userId);

    boolean existsByUser_NicknameAndArticleId(String nickname, String articleId);

    void deleteById(Long id);

    void deleteByUser_NicknameAndArticleId(String nickname, String articleId);

}
