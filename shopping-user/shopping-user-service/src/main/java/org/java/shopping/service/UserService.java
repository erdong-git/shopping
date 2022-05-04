package org.java.shopping.service;

import org.java.shopping.entity.User;
import org.java.shopping.enums.ShoppingExceptionEnum;
import org.java.shopping.exception.ShoppingException;
import org.java.shopping.mapper.UserMapper;
import org.java.shopping.util.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
/**
 * 用户中心的服务类
 */
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加一个新的User至数据库
     * 为改用户设置创建时间及密码加盐
     * 将加的盐记录进数据库
     * @param user
     */
    public Boolean registerUser(User user){

        //设置用户创建的时间
        user.setCreated(new Date());
        //使用CodeUtils类生成盐
        String salt = CodecUtils.generateSalt();
        //使用md5加盐方式对用户输入的密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(),salt));
        //将盐保存到数据库
        user.setSalt(salt);

        return  userMapper.insertSelective(user)==1;
    }


    /**
     * 根据type来选择查询用户名或者手机是否已经存在，存在则返回false，不存在则返回true
     * @param data 数据
     * @param type 查询的类型 1=查询用户名 2=查询手机号
     * @return
     */
    public Boolean checkUserNameAndPhone(String data,Integer type){

        User recode = new User();
        if(type==1){
            recode.setUsername(data);
        }else if(type==2){
            recode.setPhone(data);
        }else{
            return null;
        }

        User user = userMapper.selectOne(recode);
        if (user==null){
            return true;
        }else{
            return false;
        }


    }

    /**
     * 根据username查询是否存在，如果存在则取出salt与用户输入的密码进行对比
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password){

        //创建一个对象封装信息
        User recode = new User();
        //设置用户输入的username
        recode.setUsername(username);
        //查询数据库返回对应的信息
        User user=userMapper.selectOne(recode);
        if(user==null){
            //如果返回结果为空，则该用户名不存在
            return null;
        }
        //取出盐来对比密码
        String salt=user.getSalt();
        if(!CodecUtils.md5Hex(password,salt).equals(user.getPassword())){
            return  null;
        }
        return user;

    }




}
