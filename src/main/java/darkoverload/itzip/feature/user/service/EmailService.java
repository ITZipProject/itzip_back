package darkoverload.itzip.feature.user.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
