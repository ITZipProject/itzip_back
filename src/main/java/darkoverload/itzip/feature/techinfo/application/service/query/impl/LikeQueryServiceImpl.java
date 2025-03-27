package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.techinfo.application.service.query.LikeQueryService;
import darkoverload.itzip.feature.techinfo.domain.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LikeQueryServiceImpl implements LikeQueryService {

    private final LikeRepository likeRepository;

    public LikeQueryServiceImpl(final LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public boolean existsByUserNicknameAndArticleId(final String nickname, final String articleId) {
        return likeRepository.existsByUser_NicknameAndArticleId(nickname, articleId);
    }

}
