package darkoverload.itzip.feature.resume.controller.response;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.Builder;

public record SearchResumeResponse(Long resumeId, String subject, PublicOnOff publicOnOff, String imageUrl, Long userId) {


    @Builder
    public SearchResumeResponse {

    }

    public static SearchResumeResponse from(ResumeEntity entity) {
        return SearchResumeResponse.builder()
                .resumeId(entity.getId())
                .subject(entity.getSubject())
                .publicOnOff(entity.getPublicOnOff())
                .imageUrl(entity.getImageUrl())
                .build();
    }

}
