package darkoverload.itzip.feature.algorithm.service;

import darkoverload.itzip.feature.algorithm.service.problem.SaveProblems;
import darkoverload.itzip.feature.algorithm.service.tag.SaveTags;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    private final SaveTags saveTags;
    private final SaveProblems saveProblems;

    public AlgorithmServiceImpl(
            @Qualifier("saveTagsImpl") SaveTags saveTags,
            @Qualifier("saveProblemsImpl") SaveProblems saveProblems
    ) {
        this.saveTags = saveTags;
        this.saveProblems = saveProblems;
    }

    @Override
    public void saveProblemTags() {
        saveTags.saveProblemTags();
    }

    @Override
    public void saveProblems() {
        saveProblems.saveProblems();
    }
}