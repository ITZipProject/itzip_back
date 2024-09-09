package darkoverload.itzip.feature.job.domain;


import darkoverload.itzip.feature.job.entity.JobInfoScrapEntity;
import darkoverload.itzip.feature.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JobInfoScrap {

    private User user;

    private JobInfo jobInfo;


    public JobInfoScrapEntity convertToEntity() {
        return JobInfoScrapEntity.builder()
                .user(user.convertToEntity())
                .jobInfo(jobInfo.toIdEntity())
                .build();
    }

    public static JobInfoScrap createScrap(User user, JobInfo jobinfo) {
        return JobInfoScrap.builder()
                .user(user)
                .jobInfo(jobinfo)
                .build();
    }
}
