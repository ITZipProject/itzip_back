package darkoverload.itzip.user.controller;


import darkoverload.itzip.feature.user.service.EmailService;
import darkoverload.itzip.feature.user.service.UserService;
import darkoverload.itzip.feature.user.service.VerificationService;
import darkoverload.itzip.feature.user.util.RandomAuthCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class UserControllerTest {
    private final UserService userService;
    private final EmailService emailService;
    private final VerificationService verificationService;
    private final String email = "test@naver.com"; // 여러분의 이메일을 넣어서 테스트 해보세요^^
    private String authCode; // 여러분의 이메일을 넣어서 테스트 해보세요^^

    @Autowired
    UserControllerTest(UserService userService, EmailService emailService, VerificationService verificationService) {
        this.userService = userService;
        this.emailService = emailService;
        this.verificationService = verificationService;
    }

    @Test
    @DisplayName("랜덤 닉네임 생성 Test")
    void createRandomNickname() {
        String newName = userService.getUniqueNickname();
        log.info("당신의 이름은 이제부터 \"{}\"입니다.", newName);
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