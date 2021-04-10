package handler.returnValueHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Return Value Handler 는 현재 ViewResolve Handler 의 역할과 거의 유사합니다.
 * Controller 에서 반환된 Return 값을 어떤 View 로 Resolve 해 줄지를 판단하는 Controller 로서
 * 후에 JsonConvertor 나 ObjectSerialization 등을 개발 할 시 Interface 로 추상화된 뒤,
 * ViewResolveHandler 등으로 분리될 예정입니다.
 */
public class ReturnValueHandler {

    private static final Logger log = LoggerFactory.getLogger(ReturnValueHandler.class);

    private final String returnValue;
    private final DataOutputStream dos;

    private static final String PREFIX = "./webapp/";

    public ReturnValueHandler(Object returnValue, DataOutputStream dos) {
        this.returnValue = (String) returnValue;
        this.dos = dos;
    }

    /**
     * Redirection 이 먼져 포맣되어 있는지 확인하며, Redirection 이 없을 시 기존 Response 로 응답합니다.
     * @throws IOException
     */
    public void handle() throws IOException {
        if(returnValue.startsWith("redirect")) {
            String url = returnValue.split(":")[1];
            response302Header(dos, url);
        }else {
            byte[] body = Files.readAllBytes(new File(PREFIX + returnValue).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        }
    }

    /**
     * Css 파일과 같은 정적 파일을 응답해줄때 이용됩니다/
     * @param dos DataOutPutStream
     * @param url 요청 Url 을 의미합니다.
     * @throws IOException
     */
    public static void responseStaticFile(DataOutputStream dos, String url) throws IOException {
        byte[] body = Files.readAllBytes(new File(PREFIX + url).toPath());
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8 \r\n");
            dos.writeBytes(String.format("Content-Length: %d \r\n", body.length));
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Redirection 응답을 만들어줄때 사용합니다.
     * @param dos DataOutputStream
     * @param location 어떤 Url 로 Redirection 될지 결정합니다.
     * @param cookie 어떤 Cookie 를 넣어서 응답해줄지 결정합니다.
     */
    private void response302HeaderWithCookies(DataOutputStream dos, String location, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 \r\n");
            dos.writeBytes(String.format("Location: %s \r\n", location));
            dos.writeBytes(String.format("Set-Cookie: %s \r\n", cookie));
            dos.writeBytes("\r\n");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Redirection 응답을 만들어줄때 사용합니다.
     * @param dos DataOutputStream
     * @param location 어떤 Url 로 Redirection 될지 결정합니다.
     */
    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 \r\n");
            dos.writeBytes(String.format("Location: %s \r\n", location));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 정상적으로 성공했을시 반환하는 응답입니다.
     * @param dos DataOutputStream
     * @param lengthOfBodyContent Content-Body Length
     */
    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
