package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static core.Cookie.COOKIE;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private InputStream inputStream;
    private String url;
    private String method;
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

        this.method = fistLine[0];
        this.url = fistLine[1];

        header.saveHeaders(br, line);

        log.info("method : " + method);
        log.info("url : " + url);

        int queryStringStartIndex = url.indexOf("?");

        if(queryStringStartIndex != -1) {
            parameters = HttpRequestUtils.parseQueryString(url.substring(queryStringStartIndex + 1, url.length()));
            url = url.substring(0, queryStringStartIndex);
        }

        if(!method.equals("GET")) {
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

    public String getMethod() {
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
