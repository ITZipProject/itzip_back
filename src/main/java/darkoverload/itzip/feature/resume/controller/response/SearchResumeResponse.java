package darkoverload.itzip.feature.resume.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SearchResumeResponse(Long resumeId, String subject, PublicOnOff publicOnOff, String imageUrl, Long userId, String workLongTerm) {


    @Builder
    public SearchResumeResponse {

    }

    public static SearchResumeResponse from(Resume resume) {
        return SearchResumeResponse.builder()
                .resumeId(resume.getResumeId())
                .subject(resume.getSubject())
                .publicOnOff(resume.getPublicOnOff())
                .imageUrl(resume.getImageUrl())
                .workLongTerm(resume.getWorkLongTerm())
                .build();
    }

}
