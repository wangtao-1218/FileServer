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
		// ���Ҫ������Դid
        String id = request.getParameter("id");
        // �������ݿ��ѯ��Դ��Ϣ
        UserDao ud=new UserDaoImpl();
        User user=new User();
        try {
			user=ud.findUserbyid(id);
			
            if (user == null) {
                throw new RuntimeException("������Դ��id�����ڣ�");
            } else {
            	String realname = user.getRealname();
                String savepath = user.getSavepath();
                // ���user��Դ��Ϣ
            	File file = new File(savepath + "\\" + realname);
                if (file.exists()) {
                    // �ļ����ڣ���������
                    // ����Content-Type
                    response.setContentType(getServletContext().getMimeType(user.getRealname()));
                    // ����Content-Disposition
                    String userAgent = request.getHeader("user-agent").toLowerCase();  
  
                 if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
                  // win10 ie edge ����� ������ϵͳ��ie  
                    realname = URLEncoder.encode(realname, "UTF-8");  
                    } else {  
                  // ��IE  
                    realname = new String(realname.getBytes("UTF-8"), "iso-8859-1");  
                            }
                    response.setHeader("Content-Disposition","attachment;filename=" + realname);
                    // �ļ���������
                  //��ȡҪ���ص��ļ������浽�ļ�������
                    FileInputStream in = new FileInputStream(savepath + "\\" + realname);
                  //���������
                    OutputStream out = response.getOutputStream();
                  //����������
                     byte buffer[] = new byte[1024];
                     int len = 0;
                  //ѭ�����������е����ݶ�ȡ������������
                      while((len=in.read(buffer))>0){
                  //��������������ݵ��������ʵ���ļ�����
                        out.write(buffer, 0, len);
            		}
                        in.close();
                        out.close();
                }else {
                    throw new RuntimeException("���������޷��ҵ�����Դ��");
                }
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("������Դʧ�ܣ�");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
