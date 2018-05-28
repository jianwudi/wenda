package com.nowcoder.controller;


import com.nowcoder.model.User;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by nowcoder on 2016/7/10.
 */
@Controller
public class IndexController {
    @RequestMapping(path = {"/index/{userId}/{profile}"}, method = {RequestMethod.GET})
    public String index(@PathVariable("userId") int userId,
                        @PathVariable("profile") String profile,
                        @RequestParam(value = "type", defaultValue = "1") int type,
                        @RequestParam(value = "key",required = true) String key)
    {
 //       return String.format("type:%d",type);

        return "home";
    }
    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
    public String template(Model model) {
        List<String> color = Arrays.asList(new String[]{"green","blue"});
        model.addAttribute("colors",color);

        Map<String,String> map = new HashMap<>();
        for(int i = 0;i<3;i++)
            map.put(String.valueOf(i),String.valueOf(i*i));
        model.addAttribute("map",map);

        User user = new User("ppp");
        model.addAttribute(user);
        return "home";
    }
    @RequestMapping(path = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("COOKIE"+sessionId);
        Enumeration<String> headerName = request.getHeaderNames();
        while(headerName.hasMoreElements())
        {
            String head = headerName.nextElement();
            sb.append(head + ":"+request.getHeader(head)+"<br>");
        }
        if(request.getCookies() != null)
        {
            for(Cookie cookie:request.getCookies())
            {
                sb.append("cookie:"+cookie.getName()+","+cookie.getValue());
            }
        }
            response.addHeader("dsfsd","sdfasf");
            response.addCookie(new Cookie("dsfsd","12314"));
        return sb.toString();
    }
    @RequestMapping(path = {"/redirect/{code}"}, method = {RequestMethod.GET})
    public RedirectView redirect(HttpSession httpSession,
                          @PathVariable("code") int code)
    {
        RedirectView rd = new RedirectView("/vm",true);
        rd.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return rd;
    }
    @RequestMapping(path = {"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin()
    {
        throw new IllegalArgumentException("cansdfdf");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e)
    {
        return "ERROR:"+e.getMessage();
    }
}