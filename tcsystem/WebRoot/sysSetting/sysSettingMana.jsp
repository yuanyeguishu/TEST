<?xml version="1.0" encoding="GB18030" ?>
<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
<title>Insert title here</title>
</head>
<body>
	<a href="baseSet!getBaseSetInfo">基础设置</a><br /><br />
	<a href="viewAllScientificModular">科研设置</a><br /><br />
	<a href="">教学设置</a><br /><br />
	<a href="">公益设置</a><br /><br />
	<input type="submit" value="back" onclick="window.location.href='back'"/>
	<s:debug></s:debug>
</body>
</html>