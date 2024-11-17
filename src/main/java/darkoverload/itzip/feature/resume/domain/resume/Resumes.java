package darkoverload.itzip.feature.resume.domain.resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resumes {
    private List<Resume> resumes;

    private Resumes(List<Resume> resumes) {
        this.resumes = resumes;
    }

    public static Resumes from(List<Resume> resumes) {
        return new Resumes(resumes);
    }

    public Map<Long, Resume> toMakeResumesMap() {
        Map<Long, Resume> resumeMaps = new HashMap<>();
        resumes.forEach(resume-> resumeMaps.put(resume.getResumeId(), resume));

        return resumeMaps;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

}
