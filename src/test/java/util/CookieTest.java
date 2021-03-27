package util;

import core.Cookie;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CookieTest {

    Cookie cookie = new Cookie();


    @Test
    public void addCookieTest() {
        cookie.addCookie("roach", "spring");
        cookie.addCookie("honux", "db");
        assertThat(cookie.toString()).isEqualTo("roach=spring, honux=db");
    }

}
