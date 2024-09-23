package darkoverload.itzip.feature.resume.controller.response;

import darkoverload.itzip.feature.resume.code.PublicOnOff;

import java.util.List;



public class UpdateResumeResponse {

    private String email;

    private String phone;

    private String subject;

    private String introduction;

    private PublicOnOff publicOnOff;

    private List<String> links;

    private Long resumeId;
}
