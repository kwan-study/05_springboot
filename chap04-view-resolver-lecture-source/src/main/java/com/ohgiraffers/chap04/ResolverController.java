package com.ohgiraffers.chap04;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResolverController {


    /* 설명. 서블릿 때와 마찬가지로 포워딩을 통해 값을 전달할 수 있다. */
    @GetMapping("string")
    public String stringReturning(Model model) {
        model.addAttribute("forwardMessage", "문자열로 뷰 이름 반환함...");

        return "result";
    }

    /* 설명. 서블릿 때와 마찬가지로 리다이렉트를 하지만 파라미터를 활용하지 않고서는 전달할 수 없다.(스프링은 해법들을 제공한다.) */
    @GetMapping("string-redirect")
    public String stringRedirect(Model model) {
        model.addAttribute("message1", "문자열로 뷰 이름 반환하며 리다이렉트...");

        return "redirect:/";
    }

    /* 설명. 스프링의 RedirectAttribute라는 객체에 attr을 담으면 리다이렉트 시에도 값(전달할 상태)이 유지된다. */
    @GetMapping("string-redirect-attr")
    public String stringRedirectFlashAttribute(RedirectAttributes rttr) {
        rttr.addFlashAttribute("flashMessage1", "리다이렉트 attr 사용하여 redirect...");

        return "redirect:/";
    }

    /* 설명. 단순 forward 반환 시에는 Model만 활용했을 때와 별 차이가 없다. (반환형이 ModelAndView로 바뀐 정도) */
    @GetMapping("modelandview")
    public ModelAndView modelAndViewTest(ModelAndView mv) {
        mv.addObject("message2", "ModelAndView를 이용한 forward");
        mv.setViewName("result");

        return mv;
    }

    /* 설명. redirect 시에는 ModelAndView에 addObject로 담긴 key와 value는 parameter로 리다이렉트 된다.(쿼리스트링 형태) */
    @GetMapping("modelandview-redirect")
    public ModelAndView modelAndViewRedirectTest(ModelAndView mv) {
        mv.addObject("message2", "ModelAndView를 이용한 redirect");
        mv.setViewName("redirect:/");

        return mv;
    }

    @GetMapping("modelandview-redirect-attr")
    public ModelAndView modelAndViewRedirectFlashAttribute(ModelAndView mv,
                                                           RedirectAttributes rttr) {
        rttr.addFlashAttribute("flashMessage2", "ModelAndView를 이용한 redirect attr");
        mv.setViewName("redirect:/");

        return mv;
    }
}
