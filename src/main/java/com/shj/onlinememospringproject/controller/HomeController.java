package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {  // 기본 도메인으로 접속시, 프론트엔드에서 로그인상태 여부에 따라 리다이렉트
        return "redirect:/login";
    }
}