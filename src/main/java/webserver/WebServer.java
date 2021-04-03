package webserver;

import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import container.BeanFactory;
import container.annotationProcessor.AutoWiredProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static BeanFactory beanFactory;
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        try {
            beanFactory = new BeanFactory();
            AutoWiredProcessor autoWiredProcessor = new AutoWiredProcessor(beanFactory);
            autoWiredProcessor.conductAutoInjection();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.submit(new RoachCat(connection));
            }
        }
    }
}
