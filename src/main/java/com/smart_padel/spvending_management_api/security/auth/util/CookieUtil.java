package com.smart_padel.spvending_management_api.security.auth.util;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
public class CookieUtil {
    CookieUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static Cookie createCookie(String name, String value, int maxAge, boolean httpOnly, boolean secure, String path, String sameSite) {
        Cookie cookieObj = new Cookie(name, value);
        cookieObj.setMaxAge(maxAge);
        cookieObj.setHttpOnly(httpOnly);
        cookieObj.setSecure(secure);
        cookieObj.setPath(path);
        cookieObj.setAttribute("SameSite", sameSite);

        return cookieObj;
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
