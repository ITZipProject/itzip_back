package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;

public interface ResumeService {

    Long save(CreateResumeRequest request);

}
