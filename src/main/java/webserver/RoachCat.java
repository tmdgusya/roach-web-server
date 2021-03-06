package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import container.annotationProcessor.AnnotationManager;
import container.annotationProcessor.DefaultAnnotationManager;
import core.Cookie;
import core.Header;
import core.request.HttpRequest;
import core.request.HttpRequestParser;
import core.response.HttpResponse;
import handler.mapping.MappingUrlHandler;
import handler.returnValueHandle.ReturnValueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 서버 어플리케이션 Thread 입니다. 요청 발생시 InputStream 과 DataOutputStream 을 분석하여 HttpRequest, HttpResponse 객체를 생성합니다.
 */
public class RoachCat extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RoachCat.class);

    private Socket connection;

    public RoachCat(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            log.info("Current Thread : " + Thread.currentThread().getName());

            //Crate HttpRequest && Parsing client Request && Create Response
            //TODO Craete One Class HttpFactory(?)
            HttpRequestParser httpRequestParser = new HttpRequestParser(in);
            HttpRequest httpRequest = httpRequestParser.parser();
            HttpResponse httpResponse = new HttpResponse(new Header(), new Cookie());

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
