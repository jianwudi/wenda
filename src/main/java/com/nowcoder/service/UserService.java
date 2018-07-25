package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
    public Map<String,Object> register(String userName,String password)
    {
        Map<String,Object> res = new HashMap<String, Object>();
        if(StringUtils.isBlank(userName))
        {
            res.put("msg","username is null");
            return res;
        }
        if(StringUtils.isBlank(password))
        {
            res.put("msg","password is null");
            return res;
        }

        User user = userDAO.selectByName(userName);

        if (user != null) {
            res.put("msg", "用户名已经被注册");
            return res;
        }
        user = new User();
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        user.setName(userName);
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        userDAO.addUser(user);
        String ticket = addLoginTicket(user.getId());
        res.put("ticket", ticket);
        return res;
    }
    public Map<String,Object> login(String userName,String password)
    {
        Map<String,Object> res = new HashMap<String, Object>();
        if(StringUtils.isBlank(userName))
        {
            res.put("msg","username is null");
            return res;
        }
        if(StringUtils.isBlank(password))
        {
            res.put("msg","password is null");
            return res;
        }
        User user = userDAO.selectByName(userName);
        if(user == null)
        {
            res.put("msg", "用户名不存在");
            return res;
        }
        if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword()))
        {
            res.put("msg", "密码错误");
            return res;
        }
        String ticket = addLoginTicket(user.getId());
        res.put("ticket", ticket);
        return res;
    }
    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
}

