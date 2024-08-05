package darkoverload.itzip.feature.user.service;

public interface VerificationService {

    void saveCode(String email, String code);

    boolean verifyCode(String email, String code);
}
