package util;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    /**
     * @param br  Request Body를 시작하는 시점
     * @param contentLength  Request Header의 Content-Length 값이다.
     * @return 읽어온 Body 를 리턴합니다.
     * @throws IOException IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
