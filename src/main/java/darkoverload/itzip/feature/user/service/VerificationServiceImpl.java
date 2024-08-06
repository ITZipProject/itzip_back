package darkoverload.itzip.feature.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 인증 관련 정보를 Redis에 저장하는 서비스 클래스
 */
@Service
public class VerificationServiceImpl implements VerificationService {

    // 키와 값을 모두 String 타입으로 처리하는 redis class
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 인증 코드 만료 시간 - 10분
    private final long VERIFICATION_CODE_EXPIRATION = 10;

    /**
     * 이메일에 대한 인증 코드를 Redis에 저장
     *
     * @param email 이메일 주소
     * @param code  인증 코드
     */
    public void saveCode(String email, String code) {
        // 이메일을 키로 사용하고, 인증 코드를 값으로 사용하여 Redis에 저장
        redisTemplate.opsForValue().set(email, code, VERIFICATION_CODE_EXPIRATION, TimeUnit.MINUTES);
    }

    /**
     * 이메일과 인증 코드를 검증
     *
     * @param email 이메일 주소
     * @param code  인증 코드
     * @return 인증 코드가 일치하면 true, 그렇지 않으면 false
     */
    public boolean verifyCode(String email, String code) {
        // Redis에서 이메일에 해당하는 저장된 인증 코드를 가져옴
        String storedCode = redisTemplate.opsForValue().get(email);
        // 입력된 인증 코드가 저장된 코드와 일치하는지 확인
        return code.equals(storedCode);
    }
}
