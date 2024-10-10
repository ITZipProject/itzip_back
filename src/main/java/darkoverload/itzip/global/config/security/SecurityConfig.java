package darkoverload.itzip.global.config.security;

import darkoverload.itzip.feature.jwt.filter.TokenAuthenticationFilter;
import darkoverload.itzip.feature.jwt.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 설정 클래스
 * 애플리케이션의 보안 구성을 정의
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // JWT util
    private final JwtTokenizer jwtTokenizer;

    // 모든 유저 허용 페이지
    String[] allAllowPage = new String[]{
            "/", // 메인페이지
            "/error", // 에러페이지
            "/test/**", // 테스트 페이지
            "/user/refreshToken", // 토큰 재발급 페이지
            "/user/authEmail", // 인증 메일 페이지
            "/user/checkDuplicateEmail", // 이메일 중복 체크 페이지
            "/swagger-ui/**", // Swagger UI
            "/v3/api-docs/**", // Swagger API docs
            "/swagger-resources/**", // Swagger resources
            "/swagger-ui.html", // Swagger HTML
            "/webjars/**",// Webjars for Swagger
            "/swagger/**",// Swagger try it out
            "/resources/image/**", // 이미지 리소스
            "/image/**", // 이미지 url 임시 허용

            //quiz 임시 허용
            "/cs-quizzes/**",
            "/cs-quiz/**",
            //알고리즘 임시허용
            "/algorithm/**",

            // 기술 정보 임시 허용
            "/tech-info/**",

            // 학교 정보 검색 허용
            "/schoolsearch",

            // 이력서 임시 허용
            "/resume",

            // 직업 정보 임시 허용
            "/job-info",
            "/job-info/scrap",
    };

    // 비로그인 유저 허용 페이지
    String[] notLoggedAllowPage = new String[]{
            "/user/login", // 로그인 페이지
            "/user/join" // 회원가입 페이지
    };

    /**
     * 보안 필터 체인
     *
     * @param http 수정할 HttpSecurity 객체
     * @return 구성된 SecurityFilterChain
     * @throws Exception HttpSecurity 구성 시 발생한 예외
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 유저별 페이지 접근 허용
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(allAllowPage).permitAll() // 모든 유저
                .requestMatchers(notLoggedAllowPage).not().authenticated() // 비로그인 유저
                .anyRequest().authenticated()
        );

        // 예외처리 구성
        http.exceptionHandling(exceptionHandling ->
                // 인증 실패 시 호출하는 AuthenticationEntryPoint 설정
                exceptionHandling.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );

        // 세션 관리 Stateless 설정(서버가 클라이언트 상태 저장x)
        http.sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // cors 허용
        http.csrf(csrf -> csrf.disable());
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
        // 로그인 폼 비활성화
        http.formLogin(auth -> auth.disable());

        // http 기본 인증(헤더) 비활성화
        http.httpBasic(auth -> auth.disable());

        // JWT 필터 사용
        http.addFilterBefore(new TokenAuthenticationFilter(jwtTokenizer), UsernamePasswordAuthenticationFilter.class);

        // SecurityFilterChain을 빌드 후 반환
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}