package user.controller;

import core.request.HttpRequest;
import core.response.HttpResponse;
import handler.mapping.Controller;
import handler.mapping.GetMapping;

@Controller
public class PostController {

    @GetMapping(url = "/qna/show")
    public String getPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "qna/show.html";
    }

}
