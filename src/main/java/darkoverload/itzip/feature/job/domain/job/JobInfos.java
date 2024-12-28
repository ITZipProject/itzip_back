package darkoverload.itzip.feature.job.domain.job;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
public class JobInfos {

    private final List<JobInfo> jobInfos;

    public JobInfos() {
        this.jobInfos = new ArrayList<>();
    }

    public JobInfos(List<JobInfo> jobInfos) {
        this.jobInfos = jobInfos;
    }

    public void add(JobInfo jobInfo) {
        jobInfos.add(jobInfo);
    }

    public int size() {
        return jobInfos.size();
    }

    public List<JobInfo> subList(int start, int end) {
        return Collections.unmodifiableList(jobInfos.subList(start, end));
    }

    public List<JobInfo> getJobInfos() {
        return jobInfos;
    }

    public Map<Long, JobInfo> maekJobInfoMap() {
        return jobInfos.stream()
                .collect(Collectors.toMap(
                        JobInfo::getPositionId,
                        jobInfo -> jobInfo,
                        (existing, replacement) -> replacement
                ));
    }

    public JobInfos getUpdateJobInfos(Map<Long, JobInfo> apiDataMap) {
        JobInfos updateJobInfos = new JobInfos();
        jobInfos.forEach(jobInfo -> {
            JobInfo apiJobInfo = apiDataMap.get(jobInfo.getPositionId());

            if(apiJobInfo != null && checkNotEquals(jobInfo, apiJobInfo)) {
                updateJobInfos.add(apiJobInfo);
            }
        });

        return updateJobInfos;
    }

    /**
     * 두 JobInfo 객체의 필드를 비교하여, 모든 필드가 다른 경우 true를 반환합니다.
     * 만약 하나라도 같은 필드가 있으면 false를 반환합니다.
     *
     * @param dbJobInfo  데이터베이스에서 조회한 JobInfo 객체
     * @param apiJobInfo API로부터 가져온 JobInfo 객체
     * @return 모든 필드가 서로 다른 경우 true, 하나라도 같은 필드가 있는 경우 false
     */
    private boolean checkNotEquals(JobInfo dbJobInfo, JobInfo apiJobInfo) {

        return !dbJobInfo.getUrl().equals(apiJobInfo.getUrl()) // URL 필드 비교
                || !dbJobInfo.getTitle().equals(apiJobInfo.getTitle()) // 제목 필드 비교
                || !dbJobInfo.getIndustryName().equals(apiJobInfo.getIndustryName()) // 산업 이름 비교
                || !dbJobInfo.getJobName().equals(apiJobInfo.getJobName()) // 직무 이름 비교
                || !dbJobInfo.getExperienceName().equals(apiJobInfo.getExperienceName()) // 경력 이름 비교
                || !dbJobInfo.getSalaryName().equals(apiJobInfo.getSalaryName()) // 급여 이름 비교
                || !dbJobInfo.getExpirationDate().equals(apiJobInfo.getExpirationDate()); // 만료 날짜 비교
    }

    public Set<Long> makeSetIds() {
        return jobInfos.stream()
                .map(JobInfo::getPositionId)
                .collect(Collectors.toSet());
    }

}
