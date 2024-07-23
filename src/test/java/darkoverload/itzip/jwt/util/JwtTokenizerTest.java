package darkoverload.itzip.jwt.util;

import darkoverload.itzip.user.entity.Authority;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JwtTokenizerTest {
    private final JwtTokenizer jwtTokenizer;
    private final String testAccessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiYXV0aG9yaXR5IjoiVVNFUiIsInVzZXJJZCI6MSwibmlja25hbWUiOiLsmqnqsJDtlZwgMTHrsojsp7ggQWxlcGgga2ltIiwiaWF0IjoxNzIxNjExNzc1LCJleHAiOjE3MjE2MTUzNzV9.YjB4NhAiItg5ae_MaGAFdVWnNkktnr3yXpkcXl4xdyiqpCJQNLi1TiWBXT1ibeAKYdGRr1CZJOc8g3XO0_I_lw";
    private final String testRefreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiYXV0aG9yaXR5IjoiVVNFUiIsInVzZXJJZCI6MSwibmlja25hbWUiOiLsmqnqsJDtlZwgMTHrsojsp7ggQWxlcGgga2ltIiwiaWF0IjoxNzIxNjEyMTEwLCJleHAiOjE3MjIyMTY5MTB9._o7vypaJ455WBytaOw4X7dqDpp9AQRBt0y1mNsb26o9Yarnskjo8hl9QfWcMSq7TRc_EmMp25knAIfg9tHDpng";

   @Autowired
    JwtTokenizerTest(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Test
    void createAccessToken() {
        String token = jwtTokenizer.createAccessToken(1L, "test@example.com", "용감한 11번째 Aleph kim", Authority.USER);
        log.info(token);
    }

    @Test
    void createRefreshToken() {
        String token = jwtTokenizer.createRefreshToken(1L, "test@example.com", "용감한 11번째 Aleph kim", Authority.USER);
        log.info(token);
    }

    @Test
    void getUserIdFromToken() {
        Long userId = jwtTokenizer.getUserIdFromToken(testAccessToken);
        log.info("userId : " + userId);
    }

    @Test
    void parseAccessToken() {
        Claims parse = jwtTokenizer.parseAccessToken(testAccessToken);
        log.info(String.valueOf(parse));
    }

    @Test
    void parseRefreshToken() {
        Claims parse = jwtTokenizer.parseRefreshToken(testRefreshToken);
        log.info(String.valueOf(parse));
    }
}