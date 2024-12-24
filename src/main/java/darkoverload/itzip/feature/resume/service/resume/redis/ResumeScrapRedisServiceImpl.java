package darkoverload.itzip.feature.resume.service.resume.redis;

import darkoverload.itzip.feature.resume.controller.request.ResumeInfoScrapRequest;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;
import darkoverload.itzip.feature.resume.repository.resume.scrap.ResumeScrapJpaRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.redis.ResumeScrapRedisCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.redis.ResumeScrapRedisReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeScrapRedisServiceImpl implements ResumeScrapRedisService {

    private final ResumeScrapJpaRepository resumeScrapJpaRepository;
    private final ResumeScrapRedisCommandRepository resumeScrapRedisCommandRepository;
    private final ResumeScrapRedisReadRepository resumeScrapRedisReadRepository;

    @Override
    public String resumeScrapToRedis(ResumeInfoScrapRequest request) {
        Long id = request.getId();
        String email = request.getEmail();

        if(resumeScrapRedisReadRepository.hasSameResumeScrap(id, email) && resumeScrapRedisReadRepository.isResumeScrapStatus(id, email)) {
            resumeScrapRedisCommandRepository.unResumeScrapFromRedis(id, email);
            resumeScrapRedisCommandRepository.decrementScrapCountFromRedis(id);
            return "이력서 스크랩을 취소하였습니다.";
        }

        Optional<ResumeScrap> optionalData = resumeScrapJpaRepository.findByResumeScrap(id, email);
        if(optionalData.isPresent()) {
            resumeScrapRedisCommandRepository.notCacheUnScrapInfoToRedis(id, email);
            resumeScrapRedisCommandRepository.decrementScrapCountFromRedis(id);
            return "이력서 스크랩으ㅏㄹ 취소하였습니다.";
        }

        resumeScrapRedisCommandRepository.saveResumeScrapToRedis(id, email);
        resumeScrapRedisCommandRepository.incrementScrapCountToRedis(id);
        return "채용정보 스크랩을 하였습니다.";
    }
}
