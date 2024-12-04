package darkoverload.itzip.feature.job.domain.job;

import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class JobInfoAggregator {
    private final JobInfos apiJobInfos;
    private final JobInfos dbJobInfos;

    public JobInfoAggregator() {
        this.apiJobInfos = new JobInfos();
        this.dbJobInfos = new JobInfos();
    }

    public JobInfoAggregator(JobInfos apiJobInfos, JobInfos dbJobInfos) {
        this.apiJobInfos = apiJobInfos;
        this.dbJobInfos = dbJobInfos;
    }

    public static JobInfoAggregator create(JobInfos apiJobInfos, JobInfos dbJobInfos) {
        return new JobInfoAggregator(apiJobInfos, dbJobInfos);
    }

    public boolean isDbJobInfosEmpty() {
        return dbJobInfos.getJobInfos().isEmpty();
    }

    public JobInfoIds makeDeleteJobInfoIds() {
        return JobInfoIds.deleteIds(dbJobInfos.makeSetIds(), apiJobInfos.makeSetIds());
    }

    public JobInfos makeUpdateJobInfos() {
        return dbJobInfos.getUpdateJobInfos(apiJobInfos.maekJobInfoMap());
    }

    public JobInfos makeSaveJobInfos() {
        Set<Long> dbSet = new HashSet<>();
        dbJobInfos.getJobInfos().stream().map(JobInfo::getPositionId).forEach(dbSet::add);

        return new JobInfos(apiJobInfos.getJobInfos().stream()
                .filter(jobInfo -> !dbSet.contains(jobInfo.getPositionId()))
                .collect(Collectors.toList()));
    }

}
