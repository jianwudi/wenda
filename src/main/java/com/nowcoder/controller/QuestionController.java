package com.nowcoder.controller;

import com.nowcoder.model.HostHolder;
import com.nowcoder.model.Question;
import com.nowcoder.service.QuestionService;
import com.nowcoder.util.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.Date;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path = "/question/add" ,method = {RequestMethod.POST})
    @ResponseBody

    public String addQuestion(@Param("title") String title,@Param("content") String content)
    {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            if(hostHolder.getUser() != null)
            {
                question.setUserId(hostHolder.getUser().getId());
            }
            else
            {
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            if(questionService.addQuestion(question) > 0)
            {
                return WendaUtil.getJSON(0);
            }

        }catch (Exception e)
        {
            logger.error("添加题目失败"+e.getMessage());
        }
        return WendaUtil.getJSON(1,"失败");
    }

}