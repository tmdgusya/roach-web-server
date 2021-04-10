package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static core.Cookie.COOKIE;

/**
 * HttpRequest 은 요청이 들어올 시 HttpRequest 정보를 저장하는 클래스 입니다.
 * url, RequestMethod Header Cookie 등등.. 여러가지 정보등을 보관합니다.
 * 현재는 HttpRequest 에서 InputStream 등의 작업을 수행하지만 Refactoring 후 다른곳에서 주입시켜줄 예정입니다.
 */
public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private InputStream inputStream;
    private String url;
    private RequestMethod method;
    private Header header = new Header();
    private Cookie cookie;
    private Map<String, String> parameters;
    private Map<String, String> requestBody;

    public HttpRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void run() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String line = br.readLine();

        if (line == null) {
            return;
        }

        final String[] fistLine = line.split(" ");

        this.method = RequestMethod.valueOf(fistLine[0]);
        this.url = fistLine[1];

        header.saveHeaders(br, line);

        int queryStringStartIndex = url.indexOf("?");

        if(queryStringStartIndex != -1) {
            parameters = HttpRequestUtils.parseQueryString(url.substring(queryStringStartIndex + 1, url.length()));
            url = url.substring(0, queryStringStartIndex);
        }

        if(!method.equals(RequestMethod.GET)) {
            requestBody = HttpRequestUtils.parseQueryString(IOUtils.readData(br, Integer.parseInt(header.getAttribute("Content-Length"))));
        }

        if (header.isExistAttribute(COOKIE)) {
            cookie.saveCookie(header.getAttribute(COOKIE));
        }

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
