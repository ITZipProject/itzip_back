package darkoverload.itzip.feature.resume.domain.language;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LanguagesTest {

    private Optional<Languages> languages;

    @BeforeEach
    void setUp() {
        languages = Languages.of(List.of(Language.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).name("잇집 토익").score("780").acquisitionDate(LocalDateTime.of(2024, 10, 25, 0, 0)).languageId(1L).build(), Language.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).name("잇집 토플").score("80").acquisitionDate(LocalDateTime.of(2024, 11, 14, 0, 0)).languageId(2L).build()));
    }

    @Test
    void 일급_컬렉션_of_메서드_저장_테스트() {
        assertThat(languages).isEqualTo(Languages.of(List.of(new LanguageDto("잇집 토익", "780", LocalDateTime.of(2024, 10, 25, 0, 0), 1L), new LanguageDto("잇집 토플", "80", LocalDateTime.of(2024, 11, 14, 0, 0), 2L)), new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)));
    }

    @Test
    void 일급_컬렉션_삭제_리스트_성공_테스트() {
        assertThat(languages.get().deleteLanguages(List.of(Language.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).name("잇집 토익").score("780").acquisitionDate(LocalDateTime.of(2024, 10, 25, 0, 0)).languageId(1L).build()))).isEqualTo(List.of(Language.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).name("잇집 토플").score("80").acquisitionDate(LocalDateTime.of(2024, 11, 14, 0, 0)).languageId(2L).build()));
    }

}