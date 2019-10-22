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
		//1.判断表单是否支持文件上传。根据：enctype="multipart/form-data"
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				if(!isMultipart){
					throw new RuntimeException("该请求不支持文件上传");
				}
				User u=new User();
				//2.创建一个DiskFileItemFactory对象
				DiskFileItemFactory factory = new DiskFileItemFactory();
				//手动设置启动临时文件夹时上传文件的大小，单位是字节
				//下面代码的作用是当上传文件的大小超过3MB时才会启用临时文件夹
				factory.setSizeThreshold(1024 * 1024 * 3);
				//指定临时文件存储的目录
				String tempPath = this.getServletContext().getRealPath("/temp");
				System.out.println(tempPath);
				factory.setRepository(new File(tempPath));
				//3.创建ServletFileUpload的对象，该对象是上传的核心组件
				ServletFileUpload sfu = new ServletFileUpload(factory);
				//4.解析request对象，并获得表单项集合
				try {
					List<FileItem> fileItems = sfu.parseRequest(request);
					//5.遍历表单项集合
					for(FileItem item : fileItems){
						if(item.isFormField()){
							//普通表单 type='text'
							String fieldName = item.getFieldName();//字段名
							String fieldValue = item.getString("utf-8");//字段值
							System.out.println(fieldName + ":" + fieldValue);
						}else{
							//上传表单项
							//获取文件名
							String fileName = item.getName();
							fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
							//得到上传文件的扩展名
							String type = fileName.substring(fileName.lastIndexOf(".")+1);
							//在文件名中添加uuid保证唯一性
							String uuidname = UUID.randomUUID() + "_" + fileName;
							//获取输入流
							InputStream is = item.getInputStream();
							//创建输出流
							String path = this.getServletContext().getRealPath("/upload");
							
							//创建多级子目录
							//获取系统时间的年月日
							LocalDate now = LocalDate.now();
							int year = now.getYear();
							int month = now.getMonthValue();
							int day = now.getDayOfMonth();
							//在upload下分别创建年、月、日三级子目录
							path = path + "/" + year + "/" + month + "/" + day;
							System.out.println(path);
							
							//创建父目录
							File parentDir = new File(path);
							//如果父目录不存在，则创建
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
							//关闭流以及删除临时文件
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
					throw new RuntimeException("文件上传失败！");
				}
				UserDao ud=new UserDaoImpl();
				try {
					ud.addUser(u);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("文件上传数据库失败！");
				}
				// 提示用户文件上传成功
		        response.setContentType("text/html;charset=utf-8");
		        response.getWriter().println("文件上传成功！");   
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
