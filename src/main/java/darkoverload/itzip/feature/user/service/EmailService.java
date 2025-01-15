package darkoverload.itzip.feature.user.service;

public interface EmailService {
    void sendSimpleMail(String to, String subject, String text);

    void sendFormMail(String to, String subject, String body);

    String setAuthForm(String authCode);

    String setPwResetMail(String resetLink, String tempPassword);
}
