package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.resume.controller.response.GetResumeDetailsResponse;
import darkoverload.itzip.feature.resume.controller.response.SearchResumeResponse;
import darkoverload.itzip.feature.resume.domain.achievement.Achievements;
import darkoverload.itzip.feature.resume.domain.activity.Activities;
import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.career.Careers;
import darkoverload.itzip.feature.resume.domain.education.Educations;
import darkoverload.itzip.feature.resume.domain.language.Languages;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.Resumes;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeReadRepository;
import darkoverload.itzip.feature.user.repository.UserRepository;
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
public class ResumeReadServiceImpl implements ResumeReadService {
    private final UserRepository userRepository;
    private final ResumeReadRepository resumeReadRepository;
    private final AchievementReadRepository achievementReadRepository;
    private final ActivityReadRepository activityReadRepository;
    private final CareerReadRepository careerReadRepository;
    private final EducationReadRepository educationReadRepository;
    private final LanguageReadRepository languageReadRepository;
    private final MySkillReadRepository mySkillReadRepository;
    private final QualificationReadRepository qualificationReadRepository;

    @Override
    public List<SearchResumeResponse> searchResumeInfos(String search, Pageable pageable) {
        Resumes resumes = Resumes.from(resumeReadRepository.searchResumeInfos(search, pageable));

        Map<Long, Resume> resumeMaps = resumes.toMakeResumesMap();

        List<Career> careers = new ArrayList<>();
        resumeMaps.keySet().forEach(resumeId -> careers.addAll(careerReadRepository.findAllByResumeId(resumeId)));

        return Careers.of(careers).orElseThrow(() -> new RestApiException(CommonExceptionCode.JOB_INFO_NOT_FOUND)).searchResumeMakeWorkPeriod(resumeMaps).stream().map(SearchResumeResponse::from).collect(Collectors.toList());
    }

    @Override
    public GetResumeDetailsResponse getResumeDetails(Long id, CustomUserDetails userDetails) {

        Resume resume = resumeReadRepository.getReferenceById(id).emptyCheck();
        resume.userIdEqualsCheck(userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_MATCH_RESUME_USERID)).getId());

        Achievements achievements = Achievements.of(achievementReadRepository.findAllByResumeId(id)).orElse(null);
        Activities activities = Activities.of(activityReadRepository.findAllByResumeId(id)).orElse(null);
        Careers careers = Careers.of(careerReadRepository.findAllByResumeId(id)).orElse(null);
        Educations educations = Educations.of(educationReadRepository.findAllByResumeId(id)).orElse(null);
        Languages languages = Languages.of(languageReadRepository.findAllByResumeId(id)).orElse(null);
        MySkills mySkills = MySkills.of(mySkillReadRepository.findByAllResumeId(id)).orElse(null);
        Qualifications qualifications = Qualifications.of(qualificationReadRepository.findAllByResumeId(id)).orElse(null);

        return GetResumeDetailsResponse.of(resume, achievements, activities, careers, educations, languages, mySkills, qualifications);
    }

}
