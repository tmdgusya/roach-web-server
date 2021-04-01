package user.controller;

import handler.mapping.Controller;
import handler.mapping.GetMapping;
import handler.mapping.PostMapping;

@Controller
public class TestController {

    @GetMapping(url = "/test")
    public String get() {
        return "index";
    }

    @PostMapping(url = "/test")
    public String post() {
        return "index";
    }

}
