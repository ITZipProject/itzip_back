package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.dto.resume.ResumeDto;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Resume {
    // 이메일
    private String email;

    // 핸드폰
    private String phone;

    // 제목
    private String subject;

    // 소개글
    private String introduction;

    // 공개여부
    private PublicOnOff publicOnOff;

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

    public Resume() {
    }

    @Builder
    public Resume(String email, String phone, String subject, String introduction, PublicOnOff publicOnOff, List<String> links, String imageUrl, Long userId, Long resumeId, String workLongTerm, List<String> fileUrls) {
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.introduction = introduction;
        this.publicOnOff = publicOnOff;
        this.links = links;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.resumeId = resumeId;
        this.workLongTerm = workLongTerm;
        this.fileUrls = fileUrls;
    }

    public static Resume create(ResumeDto resume, Long userId){

        return Resume.builder()
                .email(resume.getEmail())
                .phone(resume.getPhone())
                .subject(resume.getSubject())
                .introduction(resume.getIntroduction())
                .publicOnOff(resume.getPublicOnOff())
                .links(resume.getLinks())
                .imageUrl(resume.getImageUrl())
                .userId(userId)
                .fileUrls(resume.getFileUrls())
                .build();
    }

    public static Resume update(ResumeDto resume, Long resumeId, Long userId) {
        return Resume.builder()
                .email(resume.getEmail())
                .phone(resume.getPhone())
                .subject(resume.getSubject())
                .introduction(resume.getIntroduction())
                .publicOnOff(resume.getPublicOnOff())
                .links(resume.getLinks())
                .imageUrl(resume.getImageUrl())
                .resumeId(resumeId)
                .userId(userId)
                .build();
    }

    public static Resume searchResume(Resume resume, String workLongTerm) {
        return Resume.builder()
                .resumeId(resume.getResumeId())
                .email(resume.getEmail())
                .phone(resume.getPhone())
                .subject(resume.getSubject())
                .introduction(resume.getIntroduction())
                .links(resume.getLinks())
                .imageUrl(resume.getImageUrl())
                .userId(resume.getUserId())
                .workLongTerm(workLongTerm)
                .build();
    }

    public ResumeEntity toEntity() {
        return ResumeEntity.builder()
                .email(this.email)
                .phone(this.phone)
                .subject(this.subject)
                .introduction(this.introduction)
                .publicOnOff(this.publicOnOff)
                .userId(this.userId)
                .imageUrl(this.imageUrl)
                .links(this.links)
                .userId(this.userId)
                .id(this.resumeId)
                .fileUrls(this.fileUrls)
                .build();
    }

    public Resume emptyCheck() {
        if(resumeId == null) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_RESUME);
        }
        return this;
    }

    public void userIdEqualsCheck(Long databaseUserId) {
        if(!this.userId.equals(databaseUserId)) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_RESUME_USERID);
        }
    }

}
