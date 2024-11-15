package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ResumesTest {

    @Test
    void toMakeResumesMap_변환_로직을_검증() {
        Resumes resumes = Resumes.from(List.of(Resume.builder().resumeId(1L).email("itzip@gmail.com").subject("잇집입니다.").phone("010-0009-3845").publicOnOff(PublicOnOff.YES).links(List.of("https://itzip.com")).workLongTerm("신입").build(), Resume.builder().resumeId(2L).email("itzip@gmail.com").subject("한잇집입니다.").phone("010-0009-2355").publicOnOff(PublicOnOff.YES).links(List.of("https://opgg.com")).workLongTerm("신입").build()));

        Map<Long, Resume> expected = Map.of(1L, Resume.builder().resumeId(1L).email("itzip@gmail.com").subject("잇집입니다.").phone("010-0009-3845").publicOnOff(PublicOnOff.YES).links(List.of("https://itzip.com")).workLongTerm("신입").build(), 2L,  Resume.builder().resumeId(2L).email("itzip@gmail.com").subject("한잇집입니다.").phone("010-0009-2355").publicOnOff(PublicOnOff.YES).links(List.of("https://opgg.com")).workLongTerm("신입").build());

        assertThat(resumes.toMakeResumesMap()).isEqualTo(expected);
    }
}