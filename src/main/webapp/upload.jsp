<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>文件上传</h1>
    <form action="/FileServer/upload" method="post" enctype="multipart/form-data">
        <table>
             <tr>
             <td> 上传用户：<input type="text" name="username"> <br/></td>
             </tr>        
            <tr>
                <td>文件上传<input type="file" name="upload" /></td>
               
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="上传" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>