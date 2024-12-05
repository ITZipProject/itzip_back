package darkoverload.itzip.feature.job.domain.job;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ToString
@EqualsAndHashCode
public class JobInfoIds {

    private final List<Long> idList;

    public JobInfoIds() {
        this.idList = new ArrayList<>();
    }

    public JobInfoIds(List<Long> idList) {
        this.idList = idList;
    }

    public static JobInfoIds deleteIds(Set<Long> dbIdSet, Set<Long> apiSet) {
        List<Long> deleteList = dbIdSet.stream()
                .filter(id -> !apiSet.contains(id))
                .toList();

        return new JobInfoIds(deleteList);
    }

    public List<Long> subList(int start, int end) {
        return Collections.unmodifiableList(idList.subList(start, end));
    }

    public int size() {
        return idList.size();
    }

    public List<Long> getIdList() {
        return idList;
    }
}
