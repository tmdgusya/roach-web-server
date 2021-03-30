package controller;

import core.HttpRequest;
import core.HttpResponse;
import handler.mapping.Controller;
import handler.mapping.GetMapping;

@Controller
public class PostController {

    @GetMapping(url = "/qna/show")
    public String getPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "qna/show.html";
    }

}
