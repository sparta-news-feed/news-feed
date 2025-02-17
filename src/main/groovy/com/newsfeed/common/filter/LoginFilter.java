package com.newsfeed.common.filter;


import com.newsfeed.common.Const;
import jakarta.servlet.*;

import java.io.IOException;

public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {
            "/",
            Const.ROOT_API_PATH + "/users/signup",
            Const.ROOT_API_PATH + "/users/login"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
