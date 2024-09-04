package darkoverload.itzip.feature.job.controller.response;

import darkoverload.itzip.feature.job.util.CustomStringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobInfoSearchResponse {

    private String title;

    private String industryName;

    private List<String> locationName;

    private List<String> jobName;

    private LocalDateTime expirationDate;

    private String experienceName;

    public JobInfoSearchResponse(String title, String industryName, String locationName,String jobName, LocalDateTime expirationDate, String experienceName) {
        this.title = title;
        this.industryName = industryName;
        this.locationName = CustomStringUtil.convertList(locationName);
        this.jobName = CustomStringUtil.convertList(jobName);
        this.expirationDate = expirationDate;
        this.experienceName = experienceName;
    }
}
