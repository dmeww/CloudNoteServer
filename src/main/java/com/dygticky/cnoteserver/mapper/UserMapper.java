package com.dygticky.cnoteserver.mapper;

import com.dygticky.cnoteserver.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username =#{account}")
    User queryByUserName(String account);

    @Insert("insert into user (username, password)values (#{username},#{password}) ")
    void addUser(User user);


    @Update("update user set ${param}  = #{value} where uid = #{uid}")
    void updateUser(int uid, String param, String value);

    @Update("update user set ${field} = #{sex} where uid = #{uid}")
    void updateUserSex(Integer uid, String field, Integer sex);

    @Select("select * from user where uid = #{uid}")
    User getLatestUser(int uid);

    @Update("update user set avatar = #{fileName} where uid = #{uid}")
    void setHasAvatar(int uid, String fileName);


}
