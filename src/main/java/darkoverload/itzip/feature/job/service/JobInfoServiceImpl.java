package darkoverload.itzip.feature.job.service;

import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;
import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.domain.JobInfo;
import darkoverload.itzip.feature.job.domain.JobInfoScrap;
import darkoverload.itzip.feature.job.entity.JobInfoEntity;
import darkoverload.itzip.feature.job.entity.JobInfoScrapEntity;
import darkoverload.itzip.feature.job.repository.JobInfoRepository;
import darkoverload.itzip.feature.job.repository.JobInfoScrapRepository;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobInfoServiceImpl implements JobInfoService{

    private final UserRepository userRepository;
    private final JobInfoRepository jobInfoRepository;
    private final JobInfoScrapRepository jobInfoScrapRepository;

    /**
     * TODO: sort 조건에 따른 페이징 처리 추가 예정.
     * @param search
     * @param category
     * @param experienceMin
     * @param experienceMax
     * @param pageable
     * @return
     */
    @Override
    public Page<JobInfoSearchResponse> searchJobInfo(String search, String category, Integer experienceMin, Integer experienceMax, Pageable pageable) {

        return jobInfoRepository.searchJobInfo(search, category, experienceMin, experienceMax, pageable);
    }


    /**
     *
     * @param request
     */
    @Transactional
    @Override
    public String jobInfoScrap(JobInfoScrapRequest request) {
        Optional<JobInfoScrapEntity> data = jobInfoScrapRepository.findByJobInfoId(request.getId(), request.getEmail());

        if(data.isPresent()) {
            cancelScrap(request, data.get());
            return "채용정보 스크랩을 취소하였습니다.";
        }


        JobInfoEntity entity = jobInfoRepository.findById(request.getId()).orElseThrow(() -> new RestApiException(CommonExceptionCode.JOB_INFO_NOT_FOUND));
        UserEntity user = userRepository.findByEmail(request.getEmail()).get();

        entity.setScrapCount(entity.getScrapCount() + 1);
        JobInfoScrap scrap = JobInfoScrap.createScrap(user.convertToDomain(), entity.convertToDomain());


        jobInfoScrapRepository.save(scrap.convertToEntity());

        return "채용정보 스크랩을 하였습니다.";
    }

    private void cancelScrap(JobInfoScrapRequest request, JobInfoScrapEntity data) {
        jobInfoScrapRepository.delete(data);
        JobInfoEntity infoEntity = jobInfoRepository.getReferenceById(request.getId());
        Integer count = infoEntity.getScrapCount()-1;
        infoEntity.setScrapCount(count);
    }


    @Transactional(readOnly = true)
    public JobInfo getById(Long id) {
        return jobInfoRepository.findById(id).orElseThrow(() -> new RestApiException(CommonExceptionCode.JOB_INFO_NOT_FOUND)).convertToDomain();
    }
}
