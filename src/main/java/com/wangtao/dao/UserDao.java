package com.wangtao.dao;



import java.util.List;

import com.wangtao.javabean.User;

public interface UserDao {
	public void addUser(User user) throws Exception;
    public List<User> listUser() throws Exception;
    public User findUserbyid(String id) throws Exception;
}
