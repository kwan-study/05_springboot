package com.ohgiraffers.chap02;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MethodMappingTestController {

    /* 설명. 핸들러 메소드(어노테이션을 활용해서 요청 방식 및 경로에 따라 각각 메소드가 작성된다.) */
//    @RequestMapping("/menu/regist")       // GET 요청 뿐 아니라 다른 방식의 요청도 처리됨(경로만 같으면)
    @RequestMapping(value = "/menu/regist", method= RequestMethod.GET)  // GET 요청만 가능
    public String registMenu(Model model) {
        System.out.println("/menu/regist 경로의 GET 요청 받아보기");

        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출하고 응답한 페이지");

        /* 설명. 핸들러 메소드에서 반환하는 String은 templates 폴더에 있는 view(html 파일)의 이름이다. */
        return "mappingResult";
    }

    @RequestMapping("/menu/modify")
    public String modifyMenu(Model model) {
        model.addAttribute("message", "POST 방식의 메뉴 수정용 핸들러 메소드 호출함...");

        return "mappingResult";
    }

    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model model) {
        model.addAttribute("message", "GET 방식의 메뉴 삭제용 핸들러 메소드 호출중...");

        return "mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model) {
        model.addAttribute("message", "POST 방식의 메뉴 삭제용 핸들러 메소드 호출중...");

        return "mappingResult";
    }
}
