package pers.zyx.sportplay.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.zyx.sportplay.bean.User;
import pers.zyx.sportplay.dao.UserDao;

import java.util.HashMap;

@RestController
public class LoginController {

    @Autowired
    UserDao userDao;
    @RequestMapping("/login")
    public String login(@RequestBody User user){
        String flag = "error";
        User us = userDao.getUserByMassage(user.getUsername(),user.getPassword());
        HashMap<String, Object> res = new HashMap<>();
        if(us != null){
            flag = "ok";
        }
        res.put("flag",flag);
        res.put("user",user);
        return JSON.toJSONString(res);
    }
}
