package com.nowcoder.controller;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @RequestMapping(path = "/question/add" ,method = {RequestMethod.POST})
    @ResponseBody

    public String addQuestion(@Param("title") String title,@Param("content") String content)
    {
        try {

        }catch (Exception e)
        {
            logger.error("添加题目失败"+e.getMessage());
        }
        return "Sss";
    }


}