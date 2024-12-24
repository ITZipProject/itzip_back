package darkoverload.itzip.feature.resume.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import darkoverload.itzip.feature.resume.domain.resume.ResumeBasicInfo;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SearchResumeResponse(Long resumeId, ResumeBasicInfo resumeBasicInfo, String imageUrl, Long userId, String workLongTerm) {


    @Builder
    public SearchResumeResponse {

    }

    public static SearchResumeResponse from(Resume resume) {
        return SearchResumeResponse.builder()
                .resumeId(resume.getResumeId())
                .resumeBasicInfo(resume.getResumeBasicInfo())
                .imageUrl(resume.getImageUrl())
                .workLongTerm(resume.getWorkLongTerm())
                .build();
    }

}
