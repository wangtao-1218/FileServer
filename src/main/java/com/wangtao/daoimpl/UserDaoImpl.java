package com.wangtao.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wangtao.dao.UserDao;
import com.wangtao.javabean.User;
import com.wangtao.util.DBUtil;

public class UserDaoImpl implements UserDao {

	public void addUser(User user) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtil.getConnection();
		ps = conn.prepareStatement("insert into User(type,realname,uuidname,time,savepath) values(?,?,?,?,?)");
		ps.setString(1, user.getType());
		ps.setString(2, user.getRealname());
		ps.setString(3, user.getUuidname());
		Date nowDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:m:s ");
		String time = sdf.format(nowDate);
		ps.setString(4, time);
		ps.setString(5,user.getSavepath());
		ps.executeUpdate();

	}
	public List<User> listUser() throws Exception {
		Connection conn = null;
		conn=DBUtil.getConnection();
		Statement ps = conn.createStatement();
		ResultSet rs  = ps.executeQuery("select * from User order by time desc");
		// 处理结果
		List<User> userList = new ArrayList<User>();
			while (rs.next()) {
			 User u = new User();
			 u.setId(rs.getInt("id"));
			 u.setType(rs.getString("type"));;
		     u.setRealname((rs.getString("realname")));
		     u.setUuidname(rs.getString("uuidname"));
		     u.settime(rs.getTimestamp("time"));
		     u.setSavepath(rs.getString("savepath"));
		     userList.add(u);
		     
					}
			return userList;
	}
	public User findUserbyid(String id) throws Exception{
		Connection conn = null;
		String sql="select * from User where id=?";
		conn=DBUtil.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ResultSet rs  = ps.executeQuery();
		// 处理结果
		User u= new User();
	    while (rs.next()) {
	    u.setId(rs.getInt("id"));
	    u.setType(rs.getString("type"));;
	    u.setRealname((rs.getString("realname")));
		u.setUuidname(rs.getString("uuidname"));
	    u.settime(rs.getTimestamp("time"));
		u.setSavepath(rs.getString("savepath"));	
	    }
		return u;
		
	   
	}

}
