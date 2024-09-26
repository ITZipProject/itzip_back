package darkoverload.itzip.feature.user.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    /**
     * 쿠키 가쟈오기
     *
     * @param request HttpServletRequest
     * @param name    쿠키 이름
     * @return 쿠키 Optional (없을 경우 빈 값 반환)
     */
    public static Optional<Cookie> findCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findAny();
        }
        return Optional.empty();
    }

    /**
     * 쿠키 값 가쟈오기
     *
     * @param request HttpServletRequest
     * @param name    쿠키 이름
     * @return 쿠키 값 Optional (없을 경우 빈 값 반환)
     */
    public static Optional<String> findCookieValue(HttpServletRequest request, String name) {
        return findCookie(request, name)
                .map(Cookie::getValue);
    }

    /**
     * 쿠키 추가 / 수정
     *
     * @param response HttpServletResponse
     * @param name     쿠키 이름
     * @param value    쿠키 값
     * @param maxAge   쿠키 유효 기간 (초 단위, 음수면 세션 쿠키)
     */
    public static void saveCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 쿠키 삭제
     *
     * @param response HttpServletResponse
     * @param name     쿠키 이름
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);  // 만료 시간을 0으로 설정하여 삭제
        response.addCookie(cookie);
    }

}
