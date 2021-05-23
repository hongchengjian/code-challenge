package com.hrs.security.config;

import com.hrs.common.api.ResultCode;
import com.hrs.common.api.ResultJson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component("jwtAuthenticationEntryPoint")
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //验证为未登陆状态会进入此方法，认证错误
        System.out.println("认证失败：" + authException.getMessage());
        if (!response.isCommitted()) {
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = response.getWriter();
            String body = ResultJson.failure(ResultCode.UNAUTHORIZED.getCode(), authException.getMessage()).toString();
            printWriter.write(body);
            printWriter.flush();
        }
    }
}
