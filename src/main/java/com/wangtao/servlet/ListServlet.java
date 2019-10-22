package com.wangtao.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wangtao.dao.UserDao;
import com.wangtao.daoimpl.UserDaoImpl;
import com.wangtao.javabean.User;


/**
 * Servlet implementation class ListServlet
 */
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao ud=new UserDaoImpl();
		List<User> list=new ArrayList<User>();
		try {
			list.addAll(ud.listUser());
			request.setAttribute("list", list);
            request.getRequestDispatcher("/download.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("²éÑ¯Êý¾Ý¿âÊ§°Ü£¡");
		}		   
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
