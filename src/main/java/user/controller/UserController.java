package user.controller;

import core.request.HttpRequest;
import core.response.HttpResponse;
import db.DataBase;
import handler.mapping.Controller;
import handler.mapping.GetMapping;
import handler.mapping.PostMapping;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.util.Map;

@Controller
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController() {
    }

    @GetMapping(url = "/")
    public String root(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "index.html";
    }

    @PostMapping(url = "/users")
    public String createUser(HttpRequest httpRequest, HttpResponse httpResponse) {

        final Map<String, String> parameters = httpRequest.getRequestBody();
        log.info("parameters : {}", parameters);

        User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        log.debug("User : {}" , user);

        DataBase.addUser(user);

//        DataOutputStream dos = new DataOutputStream(httpResponse.getOut());
        return "redirect:/";
    }

    @GetMapping(url = "/users")
    public String getUsers(HttpRequest httpRequest, HttpResponse httpResponse) {return "user/list.html";
    }

    @GetMapping(url = "/users/form")
    public String createUserForm(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "user/form.html";
    }

    @GetMapping(url = "/users/login")
    public String login(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "user/login.html";
    }

    @GetMapping(url = "/users/profile")
    public String profile(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "user/profile.html";
    }

}
