package com.ohgiraffers.chap05;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/main")
    public void main() {}

    @RequestMapping("/")
    public String mainRoot() {
        return "main";
    }
}
