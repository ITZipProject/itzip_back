package darkoverload.itzip.feature.algorithm.service;

import darkoverload.itzip.feature.algorithm.service.tag.SaveTags;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    private final SaveTags saveTags;

    public AlgorithmServiceImpl(
            @Qualifier("saveTagsImpl") SaveTags saveTags) {
        this.saveTags = saveTags;
    }

    @Override
    public void saveProblemTags() {
        saveTags.saveProblemTags();
    }
}