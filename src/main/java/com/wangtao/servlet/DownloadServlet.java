package com.wangtao.servlet;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wangtao.dao.UserDao;
import com.wangtao.daoimpl.UserDaoImpl;
import com.wangtao.javabean.User;

/**
 * Servlet implementation class DownloadServlet
 * @param <BASE64Encoder>
 */
public class DownloadServlet<BASE64Encoder> extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获得要下载资源id
        String id = request.getParameter("id");
        // 传入数据库查询资源信息
        UserDao ud=new UserDaoImpl();
        User user=new User();
        try {
			user=ud.findUserbyid(id);
			
            if (user == null) {
                throw new RuntimeException("下载资源的id不存在！");
            } else {
            	String realname = user.getRealname();
                String savepath = user.getSavepath();
                // 获得user资源信息
            	File file = new File(savepath + "\\" + realname);
                if (file.exists()) {
                    // 文件存在，可以下载
                    // 设置Content-Type
                    response.setContentType(getServletContext().getMimeType(user.getRealname()));
                    // 设置Content-Disposition
                    String userAgent = request.getHeader("user-agent").toLowerCase();  
  
                 if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
                  // win10 ie edge 浏览器 和其他系统的ie  
                    realname = URLEncoder.encode(realname, "UTF-8");  
                    } else {  
                  // 非IE  
                    realname = new String(realname.getBytes("UTF-8"), "iso-8859-1");  
                            }
                    response.setHeader("Content-Disposition","attachment;filename=" + realname);
                    // 文件内容下载
                  //读取要下载的文件，保存到文件输入流
                    FileInputStream in = new FileInputStream(savepath + "\\" + realname);
                  //创建输出流
                    OutputStream out = response.getOutputStream();
                  //创建缓冲区
                     byte buffer[] = new byte[1024];
                     int len = 0;
                  //循环将输入流中的内容读取到缓冲区当中
                      while((len=in.read(buffer))>0){
                  //输出缓冲区的内容到浏览器，实现文件下载
                        out.write(buffer, 0, len);
            		}
                        in.close();
                        out.close();
                }else {
                    throw new RuntimeException("服务器端无法找到该资源！");
                }
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("下载资源失败！");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
