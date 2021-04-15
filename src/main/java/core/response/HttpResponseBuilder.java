package core.response;

import core.Cookie;
import core.Header;

import java.util.Map;

public class HttpResponseBuilder {

    private Header header;
    private Cookie cookie;
    private Map<String, String> parameters;
    private Map<String, String> responseBody;

    public HttpResponseBuilder setHeader(Header header) {
        this.header = header;
        return this;
    }

    public HttpResponseBuilder addHeader(String key, String value) {
        this.header.addHeader(key, value);
        return this;
    }

    public HttpResponseBuilder setCookie(Cookie cookie) {
        this.cookie = cookie;
        return this;
    }

    public HttpResponseBuilder addCookie(String key, String value) {
        this.cookie.addCookie(key, value);
        return this;
    }

    public HttpResponseBuilder setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public HttpResponseBuilder addParameters(String key,  String value) {
        if(parameters.containsKey(key)) {
            throw new IllegalArgumentException("Parameter 에 해당 Key 가 이미 존재합니다.");
        }
        parameters.put(key, value);
        return this;
    }

    public HttpResponseBuilder setResponseBody(Map<String, String> responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public HttpResponseBuilder addResponseBody(String key,  String value) {
        if(responseBody.containsKey(key)) {
            throw new IllegalArgumentException("Response Body 에 해당 Key 가 이미 존재합니다.");
        }
        responseBody.put(key, value);
        return this;
    }

    public HttpResponse build() {
        return new HttpResponse(header, cookie, parameters, responseBody);
    }

}
