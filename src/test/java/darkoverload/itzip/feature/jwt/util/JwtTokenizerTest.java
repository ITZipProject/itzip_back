package darkoverload.itzip.feature.jwt.util;

import darkoverload.itzip.feature.jwt.util.JwtTokenizer;
import darkoverload.itzip.feature.user.entity.Authority;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class JwtTokenizerTest {
    private final JwtTokenizer jwtTokenizer;

    private final Long testId = 1L;
    private final String testEmail = "test@example.com";
    private final String testNickname = "용감한 11번째 Aleph kim";
    private final Authority testAuthority = Authority.USER;

   @Autowired
    JwtTokenizerTest(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Test
    void createAccessToken() {
        String testAccessToken = jwtTokenizer.createAccessToken(testId, testEmail, testNickname, testAuthority);
        log.info(testAccessToken);
    }

    @Test
    void createRefreshToken() {
        String testRefreshToken = jwtTokenizer.createRefreshToken(testId, testEmail, testNickname, testAuthority);
        log.info(testRefreshToken);
    }

    @Test
    void getUserIdFromToken() {
        String testAccessToken = jwtTokenizer.createAccessToken(testId, testEmail, testNickname, testAuthority);
        Long userId = jwtTokenizer.getUserIdFromToken(testAccessToken);
        log.info("userId : " + userId);
    }

    @Test
    void parseAccessToken() {
        String testAccessToken = jwtTokenizer.createAccessToken(testId, testEmail, testNickname, testAuthority);
        Claims parse = jwtTokenizer.parseAccessToken(testAccessToken);
        log.info(String.valueOf(parse));
    }

    @Test
    void parseRefreshToken() {
        String testRefreshToken = jwtTokenizer.createRefreshToken(testId, testEmail, testNickname, testAuthority);
        Claims parse = jwtTokenizer.parseRefreshToken(testRefreshToken);
        log.info(String.valueOf(parse));
    }
}