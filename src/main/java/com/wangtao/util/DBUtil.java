package com.wangtao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {
	private static String driverClass;
	private static String url;
	private static String username;
	private static String password;
	
	static{
		ResourceBundle rb = ResourceBundle.getBundle("jdbc");
		driverClass =rb.getString("driverClass");
		url=rb.getString("url");
		username=rb.getString("username");
		password=rb.getString("password");
		try {
			//×¢²áÇý¶¯
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, username, password);
	}

}
