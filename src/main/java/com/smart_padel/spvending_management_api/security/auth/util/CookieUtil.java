package com.smart_padel.spvending_management_api.security.auth.util;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
public class CookieUtil {
    CookieUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static String createCookie(String name, String value, int maxAge, boolean httpOnly, boolean secure, String path, String sameSite) {
        StringBuilder cookie = new StringBuilder();
        cookie.append(name).append("=").append(value).append("; ");
        cookie.append("Max-Age=").append(maxAge).append("; ");
        cookie.append("Path=").append(path).append("; ");
        if (secure) cookie.append("Secure; ");
        if (httpOnly) cookie.append("HttpOnly; ");
        if (sameSite != null && !sameSite.isBlank()) cookie.append("SameSite=").append(sameSite).append("; ");
        return cookie.toString();
    }
    public static String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
