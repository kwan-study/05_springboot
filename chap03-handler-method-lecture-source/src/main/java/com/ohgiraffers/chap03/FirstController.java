package com.ohgiraffers.chap03;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

/* 설명. 현재의 Controller 클래스에 작성할 핸들러 메소드들이 모두 /first/***의 요청을 받게 될 것이다.(보통은 도메인) */
@Controller
@RequestMapping("/first")
@SessionAttributes("id")
public class FirstController {

    /* 설명. 반환형이 void인 핸들러 메소드는 요청 경로 자체가 view의 경로 및 이름을 반환한 것으로 바로 해석 된다. */
//    @GetMapping("regist")
//    public String regist() {
//        return "/first/regist";
//    }

    @GetMapping("regist")
    public void regist(){}

    @PostMapping("regist")
    public String registMenu(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        System.out.println("name = " + name);

        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        String message
                = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 "
                + price + "원으로 등록하였습니다!!";

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @GetMapping("modify")
    public void modify(){}

    /* 설명.
     *  @RequestParam의 속성들
     *  1. defaultValue: 사용자의 입력값이 없거나("") 아니면 result의 parameter 키 값과 일치하지 않는
     *                   매개변수일 때 사용하고 싶은 값을 default 값으로 설정할 수 있다.
     *  2. name: request parameter의 키 값과 다른 매개변수 명을 사용하고 싶을 때 request parameter의
     *           키 값을 작성한다.
     *  (@RequestParam 어노테이션은 생략 가능)
    * */
    @PostMapping("modify")
    public String modifyMenu(Model model,
                             @RequestParam(defaultValue="디폴트", name="name") String modifyName,
                             @RequestParam(defaultValue="0") int modifyPrice) {
        String message = modifyName + "메뉴의 가격을 " + modifyPrice + "원으로 변경하였습니다.";
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @PostMapping("modify2")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameter) {
        String modifyName = parameter.get("name2");
        int modifyPrice = Integer.parseInt(parameter.get("modifyPrice2"));

        String message = modifyName + "메뉴의 가격을 " + modifyPrice + "원으로 변경하였습니다.";
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @GetMapping("search")
    public void search() {
    }

    /* 설명.
     *  핸들러 메소드의 매개변수에 우리가 작성한 클래스를 스프링이 객체로 만들어 주고 내부적으로
     *  setter를 활용해 값도 주입해 준다. (커맨드 객체), setter가 호출된다.
     *
     * 설명.
     *  @ModelAttribute 어노테이션에는 어트리뷰트 키 값을 지정할 수 있다.(키 값이 없을 땐 타입을 활용 가능)
    * */
    @PostMapping("search")
    public String searchMenu(@ModelAttribute("menu") MenuDTO menu) {
        System.out.println("menu = " + menu);
        return "first/searchResult";
    }

    @GetMapping("login")
    public void login() {}

    @PostMapping("login")
    public String sessionTest1(HttpSession session, @RequestParam String id) {
        session.setAttribute("id", id);
        return "first/loginResult";     // 세션이 유지되는 시간동안 세션을 이용해서 페이지 생성 가능
    }

    @GetMapping("logout1")
    public String logoutTest1(HttpSession session) {
        session.invalidate();

        return "first/loginResult";
    }

    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id) {
        model.addAttribute("id", id);

        return "first/loginResult";
    }

    /* 설명. @SessionAttribute 방식으로 session에 담긴 값은 SessionStatus에서 제공하는 setComplete으로 만료 시켜야 한다. */
    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus) {
        sessionStatus.setComplete();

        return "first/loginResult";
    }

    @GetMapping("body")
    public void body() {}

    @PostMapping("body")
    public void body(@RequestBody String body,
                       @RequestHeader("content-type") String contentType,
                       @CookieValue(value="JSESSIONID") String sessionId) {
        System.out.println("body = " + body);
        System.out.println("contentType = " + contentType);
        System.out.println("sessionId = " + sessionId);
    }
}