package core;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static core.Cookie.COOKIE;

public class HttpRequestParser {

    private final InputStream inputStream;
    private String url;
    private Header header = new Header();
    private RequestMethod method;
    private Cookie cookie;
    private Map<String, String> parameters;
    private Map<String, String> requestBody;

    public HttpRequestParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpRequest parser() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();

        String line = br.readLine();

        final String[] fistLine = line.split(" ");

        url = fistLine[1];

        method = RequestMethod.valueOf(fistLine[0]);

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

        return httpRequestBuilder
                .setUrl(url)
                .setMethod(method)
                .setHeader(header)
                .setCookie(cookie)
                .setParameters(parameters)
                .setRequestBody(requestBody)
                .build();
    }

}
