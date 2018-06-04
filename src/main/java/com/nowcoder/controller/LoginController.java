package com.nowcoder.controller;

import com.nowcoder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String reglogin()
    {
        return "login";
    }
    @RequestMapping(path = {"/reg"}, method = {RequestMethod.POST})
    public String reg(Model model, @RequestParam("username") String username,@RequestParam("password") String password)
    {
        try {
//            Map<String, Object> map = new HashMap<>();
           Map<String, Object> map = userService.register(username, password);
/*            if (map.get("msg") != null)
                model.addAttribute("msg", map.get("msg"));*/
            return "login";
        }catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            model.addAttribute("msg", "服务器错误");
            return "login";
        }
    }

}
