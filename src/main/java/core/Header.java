package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Header {

    private final Map<String, String> headers = new HashMap<>();

    public void saveHeaders(BufferedReader br, String line) throws IOException {
        while (!"".equals(line)) {
            line = br.readLine();
            String[] headerTokens = line.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
    }

    public boolean isExistAttribute(String attribute) {
        return headers.containsKey(attribute);
    }

    public String getAttribute(String key) {
        if(headers.containsKey(key)) {
            return headers.get(key);
        }
        throw new NoSuchElementException("해당 헤더를 찾을 수 없습니다.");
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
