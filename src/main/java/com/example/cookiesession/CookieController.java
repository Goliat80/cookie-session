package com.example.cookiesession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class CookieController {

    @GetMapping("/")
    public String index(HttpServletResponse response) {
        Cookie cookie = new Cookie("name", "andreas");
        Cookie cookie2 = new Cookie("age", "10");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1000);
        response.addCookie(cookie);
        response.addCookie(cookie2);
        return "index";
    }

    @GetMapping("/cookie")
    public String cookies(HttpServletRequest request, @CookieValue(value="hej", required=false) String nameCookie) {
        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies) {
            System.out.println(c.getValue());
        }
        if(nameCookie != null) {
            System.out.println(nameCookie);
        }
        return "index";
    }

    @GetMapping("/session")
    public String session(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = new User("Gunnar", 42);
        session.setAttribute("language", "sv");
        session.setAttribute("user", user);
        return "index";
    }
    @GetMapping("/lang")
    public String language(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        System.out.println(session.getAttribute("language"));
        return "index";
    }
    @GetMapping("/removesessioncookie")
    public String removeSessionCookie(HttpServletResponse response) {
        Cookie sessionCookie = new Cookie("JSESSIONID", "");
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
        return "form";
    }

    @GetMapping("/form")
    public String getForm() {

        return "form";
    }
    @PostMapping("/form")
    public String postForm(@RequestParam(name="language", required = false, defaultValue = "sv") String language) {
        System.out.println(language);
        return "form";
    }
}
