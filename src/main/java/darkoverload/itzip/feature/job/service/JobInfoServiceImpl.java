package darkoverload.itzip.feature.job.service;

import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.repository.JobInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobInfoServiceImpl implements JobInfoService{

    private final JobInfoRepository repository;

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

        return repository.searchJobInfo(search, category, experienceMin, experienceMax, pageable);
    }
}
