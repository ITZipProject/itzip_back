package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.controller.request.UpdateResumeRequest;
import darkoverload.itzip.feature.resume.controller.response.UpdateResumeResponse;

public interface ResumeService {
    void create(CreateResumeRequest request);

    void update(UpdateResumeRequest request);
}
