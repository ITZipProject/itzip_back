package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.controller.response.GetResumeDetailsResponse;
import darkoverload.itzip.feature.resume.controller.response.SearchResumeResponse;
import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.career.Careers;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.Resumes;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeReadRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeReadServiceImpl implements ResumeReadService{

    private final ResumeReadRepository resumeReadRepository;
    private final CareerReadRepository careerReadRepository;

    @Override
    public List<SearchResumeResponse> searchResumeInfos(String search, Pageable pageable) {
        Resumes resumes  = Resumes.from(resumeReadRepository.searchResumeInfos(search, pageable));

        Map<Long, Resume> resumeMaps = resumes.toMakeResumesMap();

        List<Career> careers = new ArrayList<>();
        resumeMaps.keySet()
                .forEach(resumeId -> careers.addAll(careerReadRepository.findAllByResumeId(resumeId)));

        return Careers.of(careers).orElseThrow(() -> new RestApiException(CommonExceptionCode.JOB_INFO_NOT_FOUND)).searchResumeMakeWorkPeriod(resumeMaps).stream().map(SearchResumeResponse::from).collect(Collectors.toList());
    }

    @Override
    public GetResumeDetailsResponse getResumeDetails(Long id) {


        return null;
    }


}
