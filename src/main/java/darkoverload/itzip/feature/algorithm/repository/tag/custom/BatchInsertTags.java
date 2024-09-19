package darkoverload.itzip.feature.algorithm.repository.tag.custom;

import java.util.List;

public interface BatchInsertTags {
    void batchInsertTags(List<Object[]> tagsToSave);
}
