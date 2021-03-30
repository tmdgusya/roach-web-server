package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import core.HttpRequest;
import core.HttpResponse;
import handler.mapping.MappingUrlHandler;
import handler.returnValueHandle.ReturnValueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoachCat extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RoachCat.class);

    private Socket connection;

    public RoachCat(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            httpRequest.run();
            HttpResponse httpResponse = new HttpResponse(out);
            MappingUrlHandler mappingUrlHandler = new MappingUrlHandler(httpRequest.getMethod(), httpRequest.getUrl());
            DataOutputStream dos = new DataOutputStream(out);
            if(httpRequest.getUrl().contains(".")) {
                ReturnValueHandler.responseStaticFile(dos, httpRequest.getUrl());
                return;
            }
            try {
                Object returnValue = mappingUrlHandler.invokeMethod(httpRequest, httpResponse);
                ReturnValueHandler returnValueHandler = new ReturnValueHandler(returnValue, dos);
                returnValueHandler.handle();
            } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException | InstantiationException e) {
                e.printStackTrace();
            }
            log.info(httpRequest.toString());
            log.info(httpResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
