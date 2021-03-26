package controller;

import core.HttpRequest;
import core.HttpResponse;
import db.DataBase;
import handler.mapping.Controller;
import handler.mapping.GetMapping;
import handler.mapping.PostMapping;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import util.ResponseData;

import java.io.DataOutputStream;
import java.util.Map;

@Controller
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController() {
    }

    @PostMapping(url = "/users")
    public void createUser(HttpRequest httpRequest, HttpResponse httpResponse) {

        final Map<String, String> parameters = httpRequest.getParameters();

        User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        log.debug("User : {}" , user);

        DataBase.addUser(user);

        DataOutputStream dos = new DataOutputStream(httpResponse.getOut());
        ResponseData.response302Header(dos, "index.html");
    }

    @GetMapping(url = "/users")
    public void getUsers(HttpRequest httpRequest, HttpResponse httpResponse) {
        log.info("12312312312");
    }

}
