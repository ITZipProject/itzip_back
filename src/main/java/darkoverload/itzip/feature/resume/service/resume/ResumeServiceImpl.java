package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.controller.request.UpdateResumeRequest;
import darkoverload.itzip.feature.resume.controller.response.CreateResumeResponse;
import darkoverload.itzip.feature.resume.controller.response.UpdateResumeResponse;
import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import darkoverload.itzip.feature.resume.domain.achievement.Achievements;
import darkoverload.itzip.feature.resume.domain.activity.Activities;
import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.career.Careers;
import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.feature.resume.domain.education.Educations;
import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.language.Languages;
import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.ResumeDetails;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeRepository;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.infra.bucket.service.AWSService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@Builder
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;

    private final EducationCommandRepository educationCommandRepository;
    private final EducationReadRepository educationReadRepository;

    private final LanguageCommandRepository languageCommandRepository;
    private final LanguageReadRepository languageReadRepository;

    private final QualificationCommandRepository qualificationCommandRepository;
    private final QualificationReadRepository qualificationReadRepository;

    private final MySkillCommandRepository mySkillCommandRepository;
    private final MySkillReadRepository mySkillReadRepository;

    private final CareerCommandRepository careerCommandRepository;
    private final CareerReadRepository careerReadRepository;

    private final AchievementCommandRepository achievementCommandRepository;
    private final AchievementReadRepository achievementReadRepository;

    private final ActivityCommandRepository activityCommandRepository;
    private final ActivityReadRepository activityReadRepository;

    private final AWSService awsService;

    @Override
    public CreateResumeResponse create(CreateResumeRequest request, CustomUserDetails user) {
        Long dataUserId = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)).getId();

        Resume.checkUserIdEquals(request.getUserId(), dataUserId);
        Resume resume = Resume.create(request.getResume(), dataUserId);

        // 이력서 저장
        // 이력서와 관련된 내용들 저장
        return CreateResumeResponse.from(create(request, resumeRepository.save(resume)));
    }

    /**
     * 제공된 요청 데이터를 기반으로 새로운 이력서의 각 섹션(경력, 성과, 활동, 언어, 학력, 기술, 자격증 등)을 생성하고 저장합니다.
     * 각 섹션은 DTO를 도메인 객체로 변환한 후, 해당 이력서와의 관계를 설정하고,
     * 이를 엔티티로 변환한 후 해당 저장소에 저장합니다.
     *
     * @param request 새로운 이력서 데이터를 포함한 CreateResumeRequest 객체입니다.
     * @param resume  새로운 정보가 저장될 Resume 객체입니다.
     */
    private ResumeDetails create(CreateResumeRequest request, Resume resume) {

        // 요청에 경력 정보가 포함된 경우 경력 섹션을 생성하고 저장
        Optional<Careers> careers = Careers.of(request.getCareers(), resume);
        Optional<Careers> dataCareers = Optional.empty();
        if (careers.isPresent()) {
            dataCareers = Careers.of(careerCommandRepository.saveAll(careers.get().getCareers()));
        }

        // 요청에 성과 정보가 포함된 경우 성과 섹션을 생성하고 저장
        Optional<Achievements> achievements = Achievements.of(request.getAchievements(), resume);
        Optional<Achievements> dataAchievements = Optional.empty();
        if (achievements.isPresent()) {
            dataAchievements = Achievements.of(achievementCommandRepository.saveAll(achievements.get().getAchievements()));
        }

        // 요청에 활동 정보가 포함된 경우 활동 섹션을 생성하고 저장
        Optional<Activities> activities = Activities.of(request.getActivities(), resume);
        Optional<Activities> dataActivities = Optional.empty();
        if (activities.isPresent()) {
            dataActivities = Activities.of(activityCommandRepository.saveAll(activities.get().getActivities()));
        }

        // 요청에 언어 정보가 포함된 경우 언어 섹션을 생성하고 저장
        Optional<Languages> languages = Languages.of(request.getLanguages(), resume);
        Optional<Languages> dataLanguages = Optional.empty();
        if (languages.isPresent()) {
            dataLanguages = Languages.of(languageCommandRepository.saveAll(languages.get().getLanguages()));
        }

        // 요청에 학력 정보가 포함된 경우 학력 섹션을 생성하고 저장
        Optional<Educations> educations = Educations.of(request.getEducations(), resume);
        Optional<Educations> dataEducations = Optional.empty();
        if (educations.isPresent()) {
            dataEducations = Educations.of(educationCommandRepository.saveAll(educations.get().getEducations()));
        }

        // 요청에 기술 정보가 포함된 경우 기술 섹션을 생성하고 저장
        Optional<MySkills> mySkills = MySkills.of(request.getMySkills(), resume);
        Optional<MySkills> dataMySkills = Optional.empty();
        if (mySkills.isPresent()) {
            dataMySkills = MySkills.of(mySkillCommandRepository.saveAll(mySkills.get().getMySkills()));
        }

        // 요청에 자격증 정보가 포함된 경우 자격증 섹션을 생성하고 저장
        Optional<Qualifications> qualifications = Qualifications.of(request.getQualifications(), resume);
        Optional<Qualifications> dataQualifications = Optional.empty();
        if (qualifications.isPresent()) {
            dataQualifications = Qualifications.of(qualificationCommandRepository.saveAll(qualifications.get().getQualifications()));
        }

        return ResumeDetails.of(dataAchievements.orElse(null), dataActivities.orElse(null), dataCareers.orElse(null), dataEducations.orElse(null), dataLanguages.orElse(null), dataMySkills.orElse(null), dataQualifications.orElse(null), resume);
    }


    @Override
    public UpdateResumeResponse update(UpdateResumeRequest request, CustomUserDetails customUserDetails) {
        long dataUserId = userRepository.findByEmail(customUserDetails.getEmail()).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)).getId();

        Resume.checkUserIdEquals(request.getUserId(), dataUserId);

        Resume updateResume = Resume.update(request.getResume(), request.getResumeId(), request.getUserId());

        Resume databaseResume = resumeRepository.findById(request.getResumeId());
        awsService.deleteDocumentFiles(updateResume.notExistFileUrls(databaseResume.getFileUrls()), Resume.FEATURE_DIR);

        return UpdateResumeResponse.from(update(request, resumeRepository.update(updateResume)));
    }

    /**
     * 제공된 업데이트 요청을 기반으로 이력서 세부 정보를 업데이트합니다.
     *
     * @param request 이력서의 새 세부 정보를 포함하는 업데이트 요청 객체입니다.
     * @param resume  업데이트할 기존 이력서 객체입니다.
     * @return 수정된 이력서의 모든 섹션을 포함하는 ResumeDetails 객체입니다.
     */
    private ResumeDetails update(UpdateResumeRequest request, Resume resume) {

        // 요청에 경력 정보가 포함된 경우 경력 섹션 업데이트
        Optional<Careers> careers = Careers.of(request.getCareers(), resume);
        Optional<Careers> dataCareers = Optional.empty();
        if (careers.isPresent()) {
            List<Career> allCareers = careerReadRepository.findAllByResumeId(resume.getResumeId());
            careerCommandRepository.deleteAllCareers(careers.get().deleteCareers(allCareers));
            dataCareers = Careers.of(careerCommandRepository.update(careers.get().getCareers()));
        }

        // 요청에 성과 정보가 포함된 경우 성과 섹션 업데이트
        Optional<Achievements> achievements = Achievements.of(request.getAchievements(), resume);
        Optional<Achievements> dataAchievements = Optional.empty();
        if (achievements.isPresent()) {
            List<Achievement> allAchievements = achievementReadRepository.findAllByResumeId(resume.getResumeId());
            achievementCommandRepository.deleteAllAchievements(achievements.get().deleteAchievements(allAchievements));
            dataAchievements = Achievements.of(achievementCommandRepository.update(achievements.get().getAchievements()));
        }

        // 요청에 활동 정보가 포함된 경우 활동 섹션 업데이트
        Optional<Activities> activities = Activities.of(request.getActivities(), resume);
        Optional<Activities> dataActivities = Optional.empty();
        if (activities.isPresent()) {
            List<Activity> allActivities = activityReadRepository.findAllByResumeId(resume.getResumeId());
            activityCommandRepository.deleteAllActivities(activities.get().deleteActivities(allActivities));
            dataActivities = Activities.of(activityCommandRepository.update(activities.get().getActivities()));
        }

        // 요청에 언어 정보가 포함된 경우 언어 섹션 업데이트
        Optional<Languages> languages = Languages.of(request.getLanguages(), resume);
        Optional<Languages> dataLanguages = Optional.empty();
        if (languages.isPresent()) {
            List<Language> allLanguages = languageReadRepository.findAllByResumeId(resume.getResumeId());
            languageCommandRepository.deleteAllLanguages(languages.get().deleteLanguages(allLanguages));
            dataLanguages = Languages.of(languageCommandRepository.update(languages.get().getLanguages()));
        }

        // 요청에 교육 정보가 포함된 경우 교육 섹션 업데이트
        Optional<Educations> educations = Educations.of(request.getEducations(), resume);
        Optional<Educations> dataEducations = Optional.empty();
        if (educations.isPresent()) {
            List<Education> allEducations = educationReadRepository.findAllByResumeId(resume.getResumeId());
            educationCommandRepository.deleteAllEducations(educations.get().deleteEducations(allEducations));
            dataEducations = Educations.of(educationCommandRepository.update(educations.get().getEducations()));
        }

        // 요청에 스킬 정보가 포함된 경우 스킬 섹션 업데이트
        Optional<MySkills> mySkills = MySkills.of(request.getMySkills(), resume);
        Optional<MySkills> dataMySkills = Optional.empty();
        if (mySkills.isPresent()) {
            List<MySkill> allMySkills = mySkillReadRepository.findByAllResumeId(resume.getResumeId());
            mySkillCommandRepository.deleteAllMySkills(mySkills.get().deleteMySkills(allMySkills));
            dataMySkills = MySkills.of(mySkillCommandRepository.update(mySkills.get().getMySkills()));
        }

        // 요청에 자격증 정보가 포함된 경우 자격증 섹션 업데이트
        Optional<Qualifications> qualifications = Qualifications.of(request.getQualifications(), resume);
        Optional<Qualifications> dataQualifications = Optional.empty();
        if (qualifications.isPresent()) {
            List<Qualification> allQualifications = qualificationReadRepository.findAllByResumeId(resume.getResumeId());
            qualificationCommandRepository.deleteAllQualifications(qualifications.get().deleteQualifications(allQualifications));
            dataQualifications = Qualifications.of(qualificationCommandRepository.update(qualifications.get().getQualifications()));
        }

        // 업데이트된 모든 섹션을 포함하는 ResumeDetails 객체 반환
        return ResumeDetails.of(dataAchievements.orElse(null), dataActivities.orElse(null), dataCareers.orElse(null), dataEducations.orElse(null), dataLanguages.orElse(null), dataMySkills.orElse(null), dataQualifications.orElse(null), resume);
    }

}
