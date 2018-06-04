package com.nowcoder.dao;

import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.Question;
import org.apache.ibatis.annotations.Insert;

public class LoginTicketDAO {
    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{user_id},#{ticket},#{expired},#{status})"})
    int addLoginTicket(LoginTicket loginTicket);
}
