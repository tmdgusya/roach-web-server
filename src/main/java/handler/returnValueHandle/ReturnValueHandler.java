package handler.returnValueHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ReturnValueHandler {

    private static final Logger log = LoggerFactory.getLogger(ReturnValueHandler.class);

    private final String returnValue;
    private final DataOutputStream dos;

    private static final String PREFIX = "./webapp/";

    public ReturnValueHandler(Object returnValue, DataOutputStream dos) {
        this.returnValue = (String) returnValue;
        this.dos = dos;
    }

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

    private void response200HeaderWithCss(DataOutputStream dos, int length) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8 \r\n");
            dos.writeBytes(String.format("Content-Length: %d \r\n", length));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 \r\n");
            dos.writeBytes(String.format("Location: %s \r\n", location));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

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
