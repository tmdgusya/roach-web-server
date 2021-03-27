package core;

import java.util.HashMap;
import java.util.Map;

public class Cookie {

    public static final String COOKIE = "cookie";

    private final Map<String, String> cookies = new HashMap<>();

    public void saveCookie(String cookie) {
        String[] cookieEntry = cookie.split("=");
        for(int i = 0; i < cookieEntry.length; i++) {
            cookies.put(cookieEntry[0], cookieEntry[1]);
        }
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String toString() {
        StringBuilder cookie = new StringBuilder();
        for(String key : cookies.keySet()) {
            cookie.append(key).append("=").append(cookies.get(key)).append(", ");

        }
        return cookie.toString().substring(0, cookie.length()-2);
    }

}
