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

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private InputStream inputStream;
    private String url;
    private String method;
    private final Map<String, String> headers = new HashMap<>();
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

        parsingHeaders(br, line);

        log.info("method : " + method);
        log.info("url : " + url);
        int parseQueryStart = url.indexOf("?");
        if(parseQueryStart != -1) {
            parameters = HttpRequestUtils.parseQueryString(url.substring(parseQueryStart + 1, url.length()));
            url = url.substring(0, parseQueryStart);
        }

        if(!method.equals("GET")) {
            requestBody = HttpRequestUtils.parseQueryString(IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length"))));
        }

    }

    private void parsingHeaders(BufferedReader br, String line) throws IOException {
        while (!"".equals(line)) {
            line = br.readLine();
            String[] headerTokens = line.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
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

    public Map<String, String> getHeaders() {
        return headers;
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
                ", headers=" + headers +
                ", parameters=" + parameters +
                ", requestBody=" + requestBody +
                '}';
    }

}
