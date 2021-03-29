package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import core.HttpRequest;
import core.HttpResponse;
import handler.mapping.MappingUrlHandler;
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
            try {
                Object returnValue = mappingUrlHandler.invokeMethod(httpRequest, httpResponse);
                //TODO 여기서 returnValue 를 resolve 하는 방식을 찾아야 한다.
                //ReturnValueHandler returnValueHandler = new ReturnValueHandler();
                //returnValueHandler.handle(returnValue); => 내부적으로 View 로 돌릴 수도 있고, 아니면 JSON 으로 바꿀수도있고 여하튼
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
