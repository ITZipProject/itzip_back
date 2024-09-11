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
     * 주어진 검색 조건에 따른 채용 정보 검색 메서드.
     *
     * 검색어, 카테고리, 경력 조건을 기준으로 채용 정보를 검색합니다.
     * 페이징 처리를 위해 Pageable 객체를 사용하여 페이지 번호, 페이지 크기, 정렬 조건을 포함할 수 있습니다.
     * 오래된 순, 최신 순, Scrap 정렬
     *
     * @param search String: 검색어 (채용 정보의 제목 또는 기타 필드를 기준으로 검색)
     * @param category String: 카테고리 (해당 카테고리에 속하는 채용 정보를 검색)
     * @param experienceMin Integer: 최소 경력 (해당 값 이상 경력을 가진 채용 정보를 필터링)
     * @param experienceMax Integer: 최대 경력 (해당 값 이하 경력을 가진 채용 정보를 필터링)
     * @param pageable Pageable: 페이지 정보와 정렬 조건을 포함한 페이징 처리 정보 (페이지 번호, 페이지 크기, 정렬 조건)
     * @return Page<JobInfoSearchResponse>: 페이징된 채용 정보 목록을 반환
     */
    @Override
    public Page<JobInfoSearchResponse> searchJobInfo(String search, String category, Integer experienceMin, Integer experienceMax, Pageable pageable) {

        return jobInfoRepository.searchJobInfo(search, category, experienceMin, experienceMax, pageable);
    }


    /**
     * 채용정보 스크랩 또는 스크랩 취소 로직을 처리하는 메서드
     *
     * 1. 사용자가 해당 채용 정보를 이미 스크랩했는지 확인
     * 2. 스크랩이 이미 존재하면 스크랩 취소 처리
     * 3. 스크랩이 존재하지 않으면 새로운 스크랩을 생성하여 저장
     *
     * @param request JobInfoScrapRequest: 채용정보 ID와 사용자 이메일을 포함한 스크랩 요청 정보
     * @return String: 스크랩 처리 결과 메시지 (스크랩 완료 또는 취소 메시지)
     */
    @Transactional
    @Override
    public String jobInfoScrap(JobInfoScrapRequest request) {
        // 1. 사용자가 해당 채용 정보를 이미 스크랩했는지 확인
        Optional<JobInfoScrapEntity> data = jobInfoScrapRepository.findByJobInfoId(request.getId(), request.getEmail());

        // 2. 스크랩이 이미 존재하면 스크랩을 취소하고 메시지를 반환
        if(data.isPresent()) {
            cancelScrap(request, data.get());
            return "채용정보 스크랩을 취소하였습니다.";
        }

        // 3. 스크랩이 존재하지 않으면 채용 정보와 사용자 정보를 조회하여 새로운 스크랩 생성
        JobInfoEntity entity = jobInfoRepository.findById(request.getId()).orElseThrow(() -> new RestApiException(CommonExceptionCode.JOB_INFO_NOT_FOUND));
        UserEntity user = userRepository.findByEmail(request.getEmail()).get();

        // 4. 채용 정보의 스크랩 카운트를 1 증가
        entity.setScrapCount(entity.getScrapCount() + 1);

        // 5. 새로운 스크랩 생성 (도메인 객체로 변환하여 생성)
        JobInfoScrap scrap = JobInfoScrap.createScrap(user.convertToDomain(), entity.convertToDomain());

        // 6. 스크랩 정보를 저장
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
