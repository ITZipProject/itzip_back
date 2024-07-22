package darkoverload.itzip.user.controller;

import darkoverload.itzip.user.service.EmailService;
import darkoverload.itzip.user.service.VerificationService;
import darkoverload.itzip.user.util.RandomAuthCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AuthEmailTest {
    private final EmailService emailService;
    private final VerificationService verificationService;
    private final String email = "dktjdej@naver.com"; // 여러분의 이메일을 넣어서 테스트 해보세요^^
    private String authCode; // 여러분의 이메일을 넣어서 테스트 해보세요^^

    @Autowired
    AuthEmailTest(EmailService emailService, VerificationService verificationService) {
        this.emailService = emailService;
        this.verificationService = verificationService;
    }

    @Test
    @DisplayName("인증번호 발송 Test")
    void sendAuthEmail() {
        // 랜덤 인증 코드 생성
        authCode = RandomAuthCode.generate();
        // redis에 인증 코드 저장
        verificationService.saveCode(email, authCode);
        // 메일 발송
        emailService.sendSimpleMessage(email, "test", authCode);

        log.info("인증 메일이 발송되었습니다.");
        log.info("인증번호 : {}", authCode);
    }

    @Test
    @DisplayName("인증번호 검증 Test")
    void checkAuthEmail() {
        boolean isVerified = verificationService.verifyCode(email, authCode);
        log.info(isVerified ? "인증이 완료되었습니다." : "인증번호를 확인해주세요.");
    }
}