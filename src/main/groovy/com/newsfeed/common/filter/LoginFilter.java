package com.newsfeed.common.filter;


import com.newsfeed.common.Const;
import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.common.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {
            "/",
            Const.ROOT_API_PATH + "/users/signup",
            Const.ROOT_API_PATH + "/users/login"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        try {
            if (!isWhiteList(requestURI)) {
                String authorization = httpRequest.getHeader("Authorization");

                if (authorization == null) {
                    throw new ApplicationException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
                } else {
                    String token = authorization.substring(7);
                    if (!JwtUtil.validateToken(token)) {
                        throw new ApplicationException("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
                    }
                }
            }

            chain.doFilter(request, response);
        } catch (ApplicationException ex) {
            httpResponse.setStatus(ex.getStatus().value());
            httpResponse.setContentType("application/json;charset=UTF-8");

            String errorBody = String.format("""
                    {
                        "status": "%s",
                        "code": "%d",
                        "message": "%s",
                        "timestamp": "%s"
                    }
                    """, ex.getStatus().name(), ex.getStatus().value(), ex.getMessage(), LocalDateTime.now());

            PrintWriter writer = httpResponse.getWriter();
            writer.println(errorBody);
            writer.flush();
        }
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
