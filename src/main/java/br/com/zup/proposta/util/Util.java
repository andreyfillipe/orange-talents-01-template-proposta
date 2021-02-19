package br.com.zup.proposta.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class Util {

    public static String getIp(HttpServletRequest request) {
        String xRealIp = request.getHeader("X-REAL-IP");
        String xForwardedFor = request.getHeader("X-FORWARDED-FOR");
        String remoteAddr = request.getRemoteAddr();

        return (xRealIp != null) ? xRealIp : ((xForwardedFor != null) ? xForwardedFor : remoteAddr);
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("USER-AGENT");
    }
}
