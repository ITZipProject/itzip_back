package darkoverload.itzip.feature.resume.repository.resume.custom;

import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomResumeReadRepository {

    List<ResumeEntity> searchResumeInfos(String search, Pageable pageable);

}
