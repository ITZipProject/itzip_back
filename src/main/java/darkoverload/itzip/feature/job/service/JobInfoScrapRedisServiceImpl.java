package darkoverload.itzip.feature.job.service;

import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.job.repository.JobInfoScrapRepository;
import darkoverload.itzip.feature.job.service.port.redis.JobInfoScrapRedisCommandRepository;
import darkoverload.itzip.feature.job.service.port.redis.JobInfoScrapRedisReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobInfoScrapRedisServiceImpl implements JobInfoScrapRedisService {
    private final JobInfoScrapRepository jobInfoScrapRepository;
    private final JobInfoScrapRedisCommandRepository jobInfoScrapRedisCommandRepository;
    private final JobInfoScrapRedisReadRepository jobInfoScrapRedisReadRepository;

    /**
     * Redis를 사용한 채용 정보 스크랩 관리 메서드입니다.
     * <p>
     * Redis에 해당 채용 정보가 이미 스크랩 상태로 저장되어 있거나 스크랩 상태가 활성화되어 있는 경우,
     * Redis에서 스크랩 정보를 제거하고 스크랩 카운트를 감소시킵니다.
     * 데이터베이스에 해당 채용 정보 스크랩이 존재하는 경우, Redis에 "캐시되지 않음" 상태로 업데이트하고 스크랩 카운트를 감소시킵니다.
     * 위 두 조건이 아닌 경우, Redis에 스크랩 정보를 저장하고 스크랩 카운트를 증가시킵니다.
     *
     * @param request 채용 정보 ID와 사용자 이메일을 포함하는 요청 객체
     * @return 채용 정보 스크랩 생성 또는 취소 메시지
     */
    @Override
    public String jobInfoScrapToRedis(JobInfoScrapRequest request) {
        Long id = request.getId();
        String email = request.getEmail();

        if (jobInfoScrapRedisReadRepository.hasSameJobInfoScrap(id, email) && jobInfoScrapRedisReadRepository.isJobInfoScrapStatus(id, email)) {
            jobInfoScrapRedisCommandRepository.unScrapInfoFromRedis(id, email);
            jobInfoScrapRedisCommandRepository.decrementScrapCountFromRedis(id);
            return "채용정보 스크랩을 취소하였습니다.";
        }

        Optional<JobInfoScrap> optionalData = jobInfoScrapRepository.findByJobInfoId(id, email);
        if (optionalData.isPresent()) {
            jobInfoScrapRedisCommandRepository.notCacheUnScrapInfoToRedis(id, email);
            jobInfoScrapRedisCommandRepository.decrementScrapCountFromRedis(id);
            return "채용정보 스크랩을 취소하였습니다.";
        }

        jobInfoScrapRedisCommandRepository.saveScrapInfoToRedis(id, email);
        jobInfoScrapRedisCommandRepository.incrementScrapCountToRedis(id);
        return "채용정보 스크랩을 하였습니다.";
    }

    /**
     * Redis에서 저장된 스크랩 키들을 가져옵니다.
     *
     * @return Redis에 저장된 스크랩 키들의 집합(Set)
     */
    @Override
    public Set<String> getScrapKeysFromRedis() {
        return jobInfoScrapRedisReadRepository.getJobInfoScrapListFromRedis();
    }

    /**
     * Redis에서 채용 정보 스크랩 데이터를 삭제하는 메서드입니다.
     * <p>
     * Redis에 저장된 특정 채용 정보 스크랩 데이터를 제거합니다.
     * 해당 채용 정보의 스크랩 카운트 데이터도 함께 삭제합니다.
     *
     * @param jobInfoId 채용 정보 ID
     * @param email     사용자 이메일
     */
    @Override
    public void jobInfoScrapDeleteToRedis(Long jobInfoId, String email) {
        jobInfoScrapRedisCommandRepository.deleteScrapInfo(jobInfoId, email);
        jobInfoScrapRedisCommandRepository.deleteScrapCountToRedis(jobInfoId);
    }

    /**
     * Redis에서 특정 채용 정보의 스크랩 카운트를 조회하는 메서드입니다.
     * <p>
     * Redis에 저장된 해당 채용 정보의 스크랩 수를 반환합니다.
     *
     * @param jobInfoId 채용 정보 ID
     * @return 해당 채용 정보의 스크랩 수 (문자열 형식)
     */
    @Override
    public String getJobInfoScrapCountFromRedis(Long jobInfoId) {
        return jobInfoScrapRedisReadRepository.getJobInfoScrapCount(jobInfoId);
    }

    /**
     * Redis에서 특정 채용 정보의 스크랩 상태를 조회하는 메서드입니다.
     * <p>
     * Redis에 저장된 해당 채용 정보와 사용자의 스크랩 상태를 반환합니다.
     *
     * @param jobInfoId 채용 정보 ID
     * @param email     사용자 이메일
     * @return 해당 채용 정보의 스크랩 상태 (SCRAP 또는 UN_SCRAP)
     */
    @Override
    public String getJobInfoStatusFromRedis(Long jobInfoId, String email) {
        return jobInfoScrapRedisReadRepository.getJobInfoStatus(jobInfoId, email);
    }

}
