package jun.studyHelper.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Log4j2
public class JwtCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // HTTP Only 쿠키에서 토큰 값을 가져오기
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        if (cookies != null)
            accessToken = Arrays.stream(cookies).filter(cookie -> cookie.equals("accessToken")).findAny().toString();

        // 가져온 토큰을 헤더에 추가
        if (accessToken != null)
            response.addHeader("Authorization", "Bearer " + accessToken);


        log.info("AccessToken", accessToken);

        filterChain.doFilter(request, response);
    }
}
