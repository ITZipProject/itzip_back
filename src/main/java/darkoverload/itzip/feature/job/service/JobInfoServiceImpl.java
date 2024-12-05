package darkoverload.itzip.feature.job.service;

import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
import darkoverload.itzip.feature.job.repository.JobInfoRepository;
import darkoverload.itzip.feature.job.repository.JobInfoScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobInfoServiceImpl implements JobInfoService{

    private final JobInfoRepository jobInfoRepository;
    private final JobInfoScrapRepository jobInfoScrapRepository;

    /**
     * 주어진 검색 조건에 따른 채용 정보 검색 메서드.
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
    public Page<JobInfoSearchResponse> searchJobInfo(String search, String category, Integer experienceMin, Integer experienceMax, String locationName, Pageable pageable) {

        return jobInfoRepository.searchJobInfo(search, category, experienceMin, experienceMax, locationName, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public JobInfo getById(Long jobInfoId) {
        return jobInfoRepository.getReferenceById(jobInfoId);
    }

    @Override
    public void jobInfoScrapSave(JobInfoScrap jobInfoScrap) {
        jobInfoScrapRepository.save(jobInfoScrap);
    }

    @Override
    public void updateScrapCount(JobInfo jobInfo) {
        jobInfoRepository.save(jobInfo);
    }

    @Override
    public void delete(JobInfoScrap jobInfoScrap) {
        jobInfoScrapRepository.delete(jobInfoScrap);
    }

    @Override
    public JobInfoScrap findByJobInfoId(Long jobInfoId, String email) {
        return jobInfoScrapRepository.findByJobInfoId(jobInfoId, email).orElse(null);
    }

}