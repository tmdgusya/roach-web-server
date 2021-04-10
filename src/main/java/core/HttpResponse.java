package core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * HttpResponse
 */
public class HttpResponse {

    private OutputStream out;

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    public OutputStream getOut() {
        return out;
    }

}
