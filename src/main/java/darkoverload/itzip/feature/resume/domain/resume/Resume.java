package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.dto.resume.ResumeDto;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Resume {
    public static final String FEATURE_DIR = "resume";
    private static final String MAP_RESUME_SCARP_COUNT_KEY = "resumeScrapCount:";

    // 이메일
    private ResumeBasicInfo resumeBasicInfo;

    // 링크
    private List<String> links;

    // 이미지
    private String imageUrl;

    // 유저아이디
    private Long userId;

    // 이력서 아이디
    private Long resumeId;

    // 경력
    private String workLongTerm;

    // url
    private List<String> fileUrls;

    private int scrapCount;

    public Resume() {
    }

    public Resume(String email, String phone, String subject, String introduction, PublicOnOff publicOnOff, List<String> links, String imageUrl, Long userId, Long resumeId, String workLongTerm, List<String> fileUrls,  int scrapCount) {
        this.resumeBasicInfo = new ResumeBasicInfo(email, phone, subject, introduction, publicOnOff);
        this.links = links;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.resumeId = resumeId;
        this.workLongTerm = workLongTerm;
        this.fileUrls = fileUrls;
        this.scrapCount = scrapCount;
    }

    @Builder
    public Resume(ResumeBasicInfo resumeBasicInfo, List<String> links, String imageUrl, Long userId, Long resumeId, String workLongTerm, List<String> fileUrls, int scrapCount) {
        this.resumeBasicInfo = resumeBasicInfo;
        this.links = links;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.resumeId = resumeId;
        this.workLongTerm = workLongTerm;
        this.fileUrls = fileUrls;
        this.scrapCount = scrapCount;
    }

    public static Resume create(ResumeDto resume, Long userId) {
        ResumeBasicInfo resumeBasicInfo = ResumeBasicInfo.builder()
                .email(resume.getEmail())
                .phone(resume.getPhone())
                .subject(resume.getSubject())
                .introduction(resume.getIntroduction())
                .publicOnOff(resume.getPublicOnOff())
                .build();

        return Resume.builder()
                .resumeBasicInfo(resumeBasicInfo)
                .links(resume.getLinks())
                .imageUrl(resume.getImageUrl())
                .userId(userId)
                .fileUrls(resume.getFileUrls())
                .scrapCount(resume.getScrapCount())
                .build();
    }

    public static Resume update(ResumeDto resume, long resumeId, long userId) {
        ResumeBasicInfo resumeBasicInfo = ResumeBasicInfo.builder()
                .email(resume.getEmail())
                .phone(resume.getPhone())
                .subject(resume.getSubject())
                .introduction(resume.getIntroduction())
                .publicOnOff(resume.getPublicOnOff())
                .build();

        return Resume.builder()
                .resumeBasicInfo(resumeBasicInfo)
                .links(resume.getLinks())
                .imageUrl(resume.getImageUrl())
                .resumeId(resumeId)
                .userId(userId)
                .fileUrls(resume.getFileUrls())
                .scrapCount(resume.getScrapCount())
                .build();
    }

    public static Resume searchResume(Resume resume, String workLongTerm) {

        return Resume.builder()
                .resumeId(resume.getResumeId())
                .resumeBasicInfo(resume.getResumeBasicInfo())
                .links(resume.getLinks())
                .imageUrl(resume.getImageUrl())
                .userId(resume.getUserId())
                .workLongTerm(workLongTerm)
                .fileUrls(resume.getFileUrls())
                .scrapCount(resume.getScrapCount())
                .build();
    }

    public ResumeEntity toEntity() {
        return ResumeEntity.builder()
                .userId(this.userId)
                .imageUrl(this.imageUrl)
                .basicInfo(this.resumeBasicInfo.toEntity())
                .links(this.links)
                .userId(this.userId)
                .id(this.resumeId)
                .fileUrls(this.fileUrls)
                .scrapCount(this.scrapCount)
                .build();
    }

    public Resume checkIdNull() {
        if (resumeId == null) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_RESUME);
        }
        return this;
    }

    public static void checkUserIdEquals(long userId, long databaseUserId) {
        if (userId != databaseUserId) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_RESUME_USERID);
        }
    }

    public List<String> notExistFileUrls(List<String> dataFileUrls) {
        return this.fileUrls.stream()
                .filter(url -> !dataFileUrls.contains(url))
                .collect(Collectors.toList());
    }

    public static String makeScrapCountRedisKey(Long jobInfoId) {
        StringBuilder sb = new StringBuilder();
        return sb.append(MAP_RESUME_SCARP_COUNT_KEY)
                .append(jobInfoId).toString();
    }

    public int updateScrapCount(int scrapCount) {
        this.scrapCount += scrapCount;
        return this.scrapCount;
    }

}
