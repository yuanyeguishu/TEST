<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:directive.page import="com.nuaa.ec.science.Permodel.TeacherLoginStatus"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 TeacherLoginStatus a = (TeacherLoginStatus)session.getAttribute("teacherloginStatus");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>NUAA����Ժ��Ч����ϵͳ - �޸�ʧ��</title>
    
    <link rel="shortcut icon" href="favicon.ico"> <link href="css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">

    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">

</head>

<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>ʧ��</h1>
        <h3 class="font-bold">������֤����</h3>

        <div class="error-desc">
            
            <br/>�����Է���
            <a href="#" onClick="javascript:history.back(-1)" class="btn btn-primary m-t">��һ��</a>
        </div>
    </div>
    <script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.5"></script>
    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>

</html>
   
