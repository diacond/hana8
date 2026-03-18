package com.hana8.hanaro.mall.security;

import com.hana8.hanaro.mall.security.exception.CustomJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                Map<String, Object> claims = jwtUtil.validateToken(token);
                String email = (String) claims.get("email");
                String role = (String) claims.get("role");

                Authentication auth = new UsernamePasswordAuthenticationToken(email, null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (CustomJwtException e) {
                // 토큰이 만료되었거나 오류가 있는 경우, Refresh 로직은 별도 엔드포인트에서 처리하므로
                // 여기서는 인증 없이 진행하거나 에러를 내려줄 수 있습니다.
                // 강사님 코드 방식대로라면 필터에서는 단순히 다음 필터로 넘깁니다.
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
