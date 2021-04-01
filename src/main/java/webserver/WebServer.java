package webserver;

import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

import container.BeanFactory;
import container.annotationProcessor.AutoWiredProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.bean.Bean1;
import user.bean.Bean3;

public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static BeanFactory beanFactory;

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
            autoWiredProcessor.conductBeanInjection();
            Bean3 bean3 = (Bean3) beanFactory.getBean(Bean3.class);
            System.out.println(bean3);
            bean3.run();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RoachCat roachCat = new RoachCat(connection);
                roachCat.start();
            }
        }
    }
}
