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

    private Long id;

    private String title;

    private String industryName;

    private List<String> locationName;

    private List<String> locationCode;

    private List<String> jobName;

    private LocalDateTime expirationDate;

    private String experienceName;

    private Long experienceMin;

    private Long experienceMax;

    private Integer scrapCount;

    private String companyName;

    private String url;

    public JobInfoSearchResponse(Long id, String title, String industryName, String locationName, String locationCode,String jobName, LocalDateTime expirationDate, String experienceName, Long experienceMin, Long experienceMax, Integer scrapCount, String companyName, String url) {
        this.id = id;
        this.title = title;
        this.industryName = industryName;
        this.locationName = CustomStringUtil.convertList(locationName);
        this.locationCode = CustomStringUtil.convertList(locationCode);
        this.jobName = CustomStringUtil.convertList(jobName);
        this.expirationDate = expirationDate;
        this.experienceName = experienceName;
        this.experienceMin = experienceMin;
        this.experienceMax = experienceMax;
        this.scrapCount = scrapCount;
        this.companyName = companyName;
        this.url = url;
    }

}
