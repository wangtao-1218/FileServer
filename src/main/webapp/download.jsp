<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>下载列表</title>
  </head>
  <body>
    <h1>文件资源列表</h1>
    <c:if test="${empty list}">
        <h2>无任何资源</h2>
    </c:if>
    <c:if test="${not empty list}">
        <c:forEach var="list" items="${list}">
            <hr/>
            <h3>资源名称：${list.realname } <a href="/FileServer/download?id=${list.id }">下载</a></h3>
            <h4>上传时间：${list.time }</h4>
        </c:forEach>
    </c:if>
  </body>
</html>