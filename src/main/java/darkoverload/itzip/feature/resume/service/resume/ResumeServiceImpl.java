package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.repository.resume.ResumeRepository;
import darkoverload.itzip.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static darkoverload.itzip.feature.resume.util.ResumeUtil.convertToBinaryString;


@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{

    private final ResumeRepository resumeRepository;

    @Override
    public Long save(CreateResumeRequest request) {
        String combination  =convertToBinaryString(request.getCombination());
        Resume resume = request.getResume().toSaveDomain(request.getUserId(), combination);

        return resumeRepository.save(resume.toEntity()).getId();
    }
}
