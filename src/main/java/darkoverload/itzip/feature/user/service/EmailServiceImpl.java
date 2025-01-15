package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    // JavaMailSender를 주입받아 이메일을 보낼 수 있도록 설정
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 간단한 이메일 메시지를 보내는 메소드
     *
     * @param to      수신자 이메일 주소
     * @param subject 이메일 제목
     * @param text    이메일 본문
     */
    public void sendSimpleMail(String to, String subject, String text) {
        // SimpleMailMessage 객체 생성 및 설정
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.format("ITZIP <%s>", fromEmail)); // 수신자 설정
        message.setTo(to); // 수신자 설정
        message.setSubject(subject); // 제목 설정
        message.setText(text); // 본문 설정
        // 이메일 전송
        mailSender.send(message);
    }

    /**
     * html 폼 메일을 발송하는 메소드
     *
     * @param to      수신자 이메일 주소
     * @param subject 이메일 제목
     * @param body    이메일 본문(html)
     */
    public void sendFormMail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setFrom(fromEmail, "ITZIP");
            messageHelper.setText(body, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 인증메일 폼에 인증코드를 추가하여 반환하는 메소드
     *
     * @param authcode 인증코드
     * @return 인증코드를 추가한 인증메일 폼
     */
    public String setAuthForm(String authcode) {
        // 인증메일 폼 경로 설정
        String templatePath = "/templates/authMailForm.html";

        // 인증메일 폼 읽어오기
        String htmlContent = null;
        try (InputStream inputStream = getClass().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
            }
            htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }

        // {{authcode}}를 실제 인증 코드로 대체
        htmlContent = htmlContent.replace("{{authcode}}", authcode);

        return htmlContent;
    }

    /**
     * 비밀번호 재설정 메일 폼에 재설정 링크와 임시 비밀번호를 추가하여 반환하는 메소드
     * @param resetLink
     * @param tempPassword
     * @return
     */
    @Override
    public String setPwResetMail(String resetLink, String tempPassword) {
        // 인증메일 폼 경로 설정
        String templatePath = "/templates/tempPasswordForm.html";

        // 인증메일 폼 읽어오기
        String htmlContent = null;
        try (InputStream inputStream = getClass().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
            }
            htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }

        // 폼에 재설정 링크와 임시 비밀번호를 추가
        htmlContent = htmlContent.replace("{{resetLink}}", resetLink);
        htmlContent = htmlContent.replace("{{tempPassword}}", tempPassword);

        return htmlContent;
    }

}
