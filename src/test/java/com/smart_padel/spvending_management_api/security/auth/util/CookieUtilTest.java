package com.smart_padel.spvending_management_api.security.auth.util;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CookieUtilTest {

    @Test
    void createCookie_createsCookieWithCorrectAttributes() {
        Cookie cookie = CookieUtil.createCookie("testName", "testValue", 3600, true, true, "/testPath");
        assertThat(cookie.getName()).isEqualTo("testName");
        assertThat(cookie.getValue()).isEqualTo("testValue");
        assertThat(cookie.getMaxAge()).isEqualTo(3600);
        assertThat(cookie.isHttpOnly()).isTrue();
        assertThat(cookie.getSecure()).isTrue();
        assertThat(cookie.getPath()).isEqualTo("/testPath");
    }

    @Test
    void getCookieValue_returnsValueWhenCookieExists() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("testName", "testValue")});
        assertThat(CookieUtil.getCookieValue(request, "testName")).isEqualTo("testValue");
    }

    @Test
    void getCookieValue_returnsNullWhenCookieDoesNotExist() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("otherName", "otherValue")});
        assertThat(CookieUtil.getCookieValue(request, "testName")).isNull();
    }

    @Test
    void getCookieValue_returnsNullWhenNoCookiesPresent() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(null);
        assertThat(CookieUtil.getCookieValue(request, "testName")).isNull();
    }

    @Test
    void constructor_ThrowsException() {
        assertThatThrownBy(CookieUtil::new)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Utility class");
    }
}