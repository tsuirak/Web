package pers.zyx.sportplay.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pers.zyx.sportplay.bean.User;

import java.util.List;

@Repository
public interface UserDao {

//    @Select("select * from user where username=#{username} and password=#{password} and state=1")
    public User getUserByMassage(@Param("username") String username, @Param("password") String password);
    public List<User> getAllUser(@Param("username") String username, @Param("pageStart") int PageStart,@Param("pageSize") int pageSize);
    public int getUserCounts(@Param("username") String username);
    public int updateState(Integer id,Boolean state);
    public int addUser(User user);
    public int deleteUser(int id);
    public User getUpdateUser(int id);
    public int editUser(User user);
}
