package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HttpRequest 은 요청이 들어올 시 HttpRequest 정보를 저장하는 클래스 입니다.
 * url, RequestMethod Header Cookie 등등.. 여러가지 정보등을 보관합니다.
 * 현재는 HttpRequest 에서 InputStream 등의 작업을 수행하지만 Refactoring 후 다른곳에서 주입시켜줄 예정입니다.
 */
public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final String url;
    private final RequestMethod method;
    private final Header header;
    private final Cookie cookie;
    private Map<String, String> parameters;
    private Map<String, String> requestBody;

    public HttpRequest(String url, RequestMethod method, Header header, Cookie cookie, Map<String, String> parameters, Map<String, String> requestBody) {
        this.url = url;
        this.method = method;
        this.header = header;
        this.cookie = cookie;
        this.parameters = parameters;
        this.requestBody = requestBody;
    }

    public static Logger getLog() {
        return log;
    }

    public String getUrl() {
        return url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", parameters=" + parameters +
                ", requestBody=" + requestBody +
                '}';
    }

}
