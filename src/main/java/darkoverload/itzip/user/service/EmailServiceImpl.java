package darkoverload.itzip.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    // JavaMailSender를 주입받아 이메일을 보낼 수 있도록 설정
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 간단한 이메일 메시지를 보내는 메소드
     *
     * @param to 수신자 이메일 주소
     * @param subject 이메일 제목
     * @param text 이메일 본문
     */
    public void sendSimpleMessage(String to, String subject, String text) {
        // SimpleMailMessage 객체 생성 및 설정
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // 수신자 설정
        message.setSubject(subject); // 제목 설정
        message.setText(text); // 본문 설정
        // 이메일 전송
        mailSender.send(message);
    }
}
