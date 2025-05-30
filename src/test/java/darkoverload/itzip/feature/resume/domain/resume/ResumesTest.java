package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ResumesTest {

    private ResumeBasicInfo resumeBasicInfoOne;
    private ResumeBasicInfo resumeBasicInfoTwo;

    @BeforeEach
    void setUp() {
        resumeBasicInfoOne = ResumeBasicInfo.builder()
                .email("itzip@gmail.com")
                .phone("010-0009-3845")
                .subject("잇집입니다.")
                .publicOnOff(PublicOnOff.YES)
                .introduction("잇집입니다. 저는 코딩이 좋아요.")
                .build();

        resumeBasicInfoTwo = ResumeBasicInfo.builder()
                .email("itzip@gmail.com")
                .subject("한잇집입니다.")
                .phone("010-0009-2355")
                .publicOnOff(PublicOnOff.YES)
                .introduction("한잇집입니다. 저는 코딩이 좋아요")
                .build();
    }

    @Test
    void toMakeResumesMap_변환_로직을_검증() {



        Resumes resumes = Resumes.from(List.of(Resume.builder().resumeId(1L).resumeBasicInfo(resumeBasicInfoOne).links(List.of("https://itzip.com")).workLongTerm("신입").build(), Resume.builder().resumeId(2L).resumeBasicInfo(resumeBasicInfoTwo).links(List.of("https://opgg.com")).workLongTerm("신입").build()));

        Map<Long, Resume> expected = Map.of(1L, Resume.builder().resumeId(1L).resumeBasicInfo(resumeBasicInfoOne).links(List.of("https://itzip.com")).workLongTerm("신입").build(), 2L,  Resume.builder().resumeId(2L).resumeBasicInfo(resumeBasicInfoTwo).links(List.of("https://opgg.com")).workLongTerm("신입").build());

        assertThat(resumes.toMakeResumesMap()).isEqualTo(expected);
    }
}