package com.wangtao.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.wangtao.dao.UserDao;
import com.wangtao.daoimpl.UserDaoImpl;
import com.wangtao.javabean.User;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.�жϱ��Ƿ�֧���ļ��ϴ������ݣ�enctype="multipart/form-data"
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				if(!isMultipart){
					throw new RuntimeException("������֧���ļ��ϴ�");
				}
				User u=new User();
				//2.����һ��DiskFileItemFactory����
				DiskFileItemFactory factory = new DiskFileItemFactory();
				//�ֶ�����������ʱ�ļ���ʱ�ϴ��ļ��Ĵ�С����λ���ֽ�
				//�������������ǵ��ϴ��ļ��Ĵ�С����3MBʱ�Ż�������ʱ�ļ���
				factory.setSizeThreshold(1024 * 1024 * 3);
				//ָ����ʱ�ļ��洢��Ŀ¼
				String tempPath = this.getServletContext().getRealPath("/temp");
				System.out.println(tempPath);
				factory.setRepository(new File(tempPath));
				//3.����ServletFileUpload�Ķ��󣬸ö������ϴ��ĺ������
				ServletFileUpload sfu = new ServletFileUpload(factory);
				//4.����request���󣬲���ñ����
				try {
					List<FileItem> fileItems = sfu.parseRequest(request);
					//5.���������
					for(FileItem item : fileItems){
						if(item.isFormField()){
							//��ͨ�� type='text'
							String fieldName = item.getFieldName();//�ֶ���
							String fieldValue = item.getString("utf-8");//�ֶ�ֵ
							System.out.println(fieldName + ":" + fieldValue);
						}else{
							//�ϴ�����
							//��ȡ�ļ���
							String fileName = item.getName();
							fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
							//�õ��ϴ��ļ�����չ��
							String type = fileName.substring(fileName.lastIndexOf(".")+1);
							//���ļ��������uuid��֤Ψһ��
							String uuidname = UUID.randomUUID() + "_" + fileName;
							//��ȡ������
							InputStream is = item.getInputStream();
							//���������
							String path = this.getServletContext().getRealPath("/upload");
							
							//�����༶��Ŀ¼
							//��ȡϵͳʱ���������
							LocalDate now = LocalDate.now();
							int year = now.getYear();
							int month = now.getMonthValue();
							int day = now.getDayOfMonth();
							//��upload�·ֱ𴴽��ꡢ�¡���������Ŀ¼
							path = path + "/" + year + "/" + month + "/" + day;
							System.out.println(path);
							
							//������Ŀ¼
							File parentDir = new File(path);
							//�����Ŀ¼�����ڣ��򴴽�
							if(!parentDir.exists()){
								parentDir.mkdirs();
							}
							
							File file = new File(path, fileName);
							FileOutputStream fos = new FileOutputStream(file);
							
							byte[] bytes = new byte[1024];
							int len = -1;
							while((len = is.read(bytes)) != -1){
								fos.write(bytes, 0, len);
							}
							//�ر����Լ�ɾ����ʱ�ļ�
							is.close();
							fos.close();
							item.delete();
							u.setType(type);
							u.setRealname(fileName);
							u.setUuidname(uuidname);
							u.setSavepath(path);
							response.setContentType("text/html;charset=utf-8");
							response.getWriter().println(uuidname);
							
							}
						}				
				} catch (FileUploadException e) {
					e.printStackTrace();
					throw new RuntimeException("�ļ��ϴ�ʧ�ܣ�");
				}
				UserDao ud=new UserDaoImpl();
				try {
					ud.addUser(u);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("�ļ��ϴ����ݿ�ʧ�ܣ�");
				}
				// ��ʾ�û��ļ��ϴ��ɹ�
		        response.setContentType("text/html;charset=utf-8");
		        response.getWriter().println("�ļ��ϴ��ɹ���");   
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
