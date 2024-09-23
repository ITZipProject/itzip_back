package darkoverload.itzip.feature.jwt.service;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        ).convertToDomain();

        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                Collections.singletonList(user.getAuthority())
        );
    }
}
