package com.ohgiraffers.chap04;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    /* 설명.
     *  ViewResolver(뷰 리졸버) 인터페이스를 구현한 ThymeleafViewResolver가 현재 처리하게 된다.
     *  접두사(prefix): resources/templates/
     *  접미사(suffex): .html
     *  핸들러 메소드가 반환하는 String 값 앞 뒤에 접두사 및 접미사를 붙여 view를 찾게 된다.
    * */
    @RequestMapping(value={"/", "/main"})      //요청으로 들어온 이름과 다르기에 void 안됨
    public String main() {
        return "main";
    }
}
