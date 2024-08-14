package com.ohgiraffers.chap05;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {
    @GetMapping("simple-null")
    public String simpleNullPointerExcpetionTest() {

        if(true) {
            throw new NullPointerException();
        }

        return "/";
    }

    @GetMapping("simple-user")
    public String simpleUserExcpetionTest() throws MemberRegistException {
        if(true) {
            throw new MemberRegistException("당신 같은 사람은 회원으로 받을 수 없습니다!");
        }
        return "/";
    }

    /* 설명. 이후 핸들러 메소드들은 이 클래스만 해당되는 지역범위 예외처리 설정 */
    @GetMapping("annotation-null")
    public String annotationNullPointeExceptionTest() {
        String str = null;
        System.out.println("str.charAt(0): " + str.charAt(0));

        return "/";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler() {
        System.out.println("이 Controller에서 NullPointerException 발생 시 여기 오는지 확인");

        return "error/nullPointer";
    }

    @GetMapping("annotation-user")
    public String userExceptionTest() throws MemberRegistException {

        if(true) {
            throw new MemberRegistException("당신은 안된다니깐???");
        }

        return "/";
    }

    /* 설명. 예외처리 핸들러에서도 Model과 발생한 예외를 활용할 수 있다. */
    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(Model model, MemberRegistException exception) {
        model.addAttribute("userException", exception);

        return "error/default";
    }
}