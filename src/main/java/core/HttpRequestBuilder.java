package core;

import java.util.Map;

public class HttpRequestBuilder {

    private String url;
    private RequestMethod method;
    private Header header = new Header();
    private Cookie cookie;
    private Map<String, String> parameters;
    private Map<String, String> requestBody;

    public HttpRequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpRequestBuilder setMethod(RequestMethod method) {
        this.method = method;
        return this;
    }

    public HttpRequestBuilder setHeader(Header header) {
        this.header = header;
        return this;
    }

    public HttpRequestBuilder addHeader(String key, String value) {
        header.addHeader(key, value);
        return this;
    }

    public HttpRequestBuilder setCookie(Cookie cookie) {
        this.cookie = cookie;
        return this;
    }

    public HttpRequestBuilder addCookie(String key, String value) {
        cookie.addCookie(key, value);
        return this;
    }

    public HttpRequestBuilder setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public HttpRequestBuilder setRequestBody(Map<String,String> requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public HttpRequest build() {
        return new HttpRequest(url, method, header, cookie, parameters, requestBody);
    }

}
