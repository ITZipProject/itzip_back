package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.techinfo.application.service.query.ScrapQueryService;
import darkoverload.itzip.feature.techinfo.domain.repository.ScrapRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ScrapQueryServiceImpl implements ScrapQueryService {

    private final ScrapRepository scrapRepository;

    public ScrapQueryServiceImpl(final ScrapRepository scrapRepository) {
        this.scrapRepository = scrapRepository;
    }

    @Override
    public boolean existsByUserNicknameAndArticleId(final String nickname, final String articleId) {
        return scrapRepository.existsByUser_NicknameAndArticleId(nickname, articleId);
    }

}
