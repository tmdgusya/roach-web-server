package core.response;

import core.Cookie;
import core.Header;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * HttpResponse
 */
public class HttpResponse {

    private final Header header;
    private final Cookie cookie;
    private Map<String, String> parameters;
    private Map<String, String> requestBody;

    public HttpResponse(Header header, Cookie cookie) {
        this.header = header;
        this.cookie = cookie;
    }

    public HttpResponse(Header header, Cookie cookie, Map<String, String> parameters, Map<String, String> requestBody) {
        this.header = header;
        this.cookie = cookie;
        this.parameters = parameters;
        this.requestBody = requestBody;
    }
}
