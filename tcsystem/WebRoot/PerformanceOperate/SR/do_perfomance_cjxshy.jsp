<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.nuaa.ec.science.Permodel.TeacherLoginStatus"/>
<jsp:directive.page import="com.nuaa.ec.science.dao.MeetingPlaceDAO"/>
<jsp:directive.page import="com.nuaa.ec.science.dao.TeacherANDJoinAcademicMeetingDAO"/>
<jsp:directive.page import="com.nuaa.ec.science.dao.MeetingTypeDAO"/>
<jsp:directive.page import="com.nuaa.ec.science.dao.PaperRetrievalConditionDAO"/>
<jsp:directive.page import="javax.servlet.http.HttpSession"/>
<jsp:directive.page import="com.nuaa.ec.science.baseSet.action.AddJAMeetingAction"/>
<jsp:directive.page import="com.nuaa.ec.science.Permodel.ViewCJXSHY"/>
<jsp:directive.page import="com.nuaa.ec.science.dao.PersonInfoDAO"/>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%

     PersonInfoDAO pidao = new PersonInfoDAO();  
     TeacherLoginStatus a = (TeacherLoginStatus)session.getAttribute("teacherloginStatus");
     MeetingTypeDAO mtdao = new MeetingTypeDAO();
     MeetingPlaceDAO mpdao = new MeetingPlaceDAO();
     PaperRetrievalConditionDAO prcdao = new PaperRetrievalConditionDAO();
     List<String> b = mtdao.getAllMeetingTypeName();
     List<String> d = mpdao.getAllMeetingPlace();
     List<String> c = prcdao.getAllPRCondition();
     TeacherANDJoinAcademicMeetingDAO dao = new TeacherANDJoinAcademicMeetingDAO();
	 List<ViewCJXSHY> f = new ArrayList<ViewCJXSHY>();
	 String teacherID = (String)request.getAttribute("teacherID");
     String teacherid = (String)request.getAttribute("teacherid");
     String id = null;
    
      if(teacherid!=null){
      id = teacherid;
      f = dao.getAllCJXSHY(id);
      System.out.println("teacherid:"+teacherid);
      }
      else f = dao.getAllCJXSHY(teacherID);
	
	 int g =-1;
	 String ttr = (String)request.getAttribute("flag");
	 if(ttr!=null){
	 		g =Integer.parseInt(ttr);
	}
	 int h =-1;
	 String hhh = (String)request.getAttribute("flag1");
	 if(hhh!=null){
	 		h =Integer.parseInt(hhh);
	 		}
  %>
 
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
   

    <title>管理员界面</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/dashboard.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">欢迎你，<%=a.getTeacherName()%></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
           
            <li><a href="#">设置</a></li>
            <li><a href="#">注销</a></li>
            <li><a href="#">帮助</a></li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar" id="accordion">
          <ul class="nav nav-sidebar">
            <li><font size="5">表格管理</a></font></li>
            <li><a class="collapsed" data-toggle="collapse" data-parent="#accordion"  href="#shangchuanbiaoge" aria-expanded="false" aria-controls="shangchuanbiaoge">上传表格</a>
			<div id="shangchuanbiaoge" class="collapse">
			  <div class="well">
				<ul class="nav nav-sidebar">
				<li><a href="">新建表格</a></li>
				<li><a href="">已生成表格</a></li>
				</ul>
			  </div>
			</div>
			</li>
			
            <li><a class="collapsed" data-toggle="collapse" data-parent="#accordion"  href="#shengchengbiaoge" aria-expanded="true" aria-controls="shengchuanbiaoge">生成表格</a></li>
          </ul>
          <ul class="nav nav-sidebar">
           <li><font size="5">任务管理</a></font></li>
            <li><a href="gljixiao.jsp">绩效查询</a></li>
            <li><a href="gladdxm.jsp">添加项目</a></li>
            <li><a href="gldeljx.jsp">删除绩效</a></li>
            <li><a href="glupdatejx.jsp">更改绩效</a></li>
          </ul>
          <ul class="nav nav-sidebar">
            <li><font size="5">查询管理</a></font></li>
           
          </ul>
		  <ul class="nav nav-sidebar">
            <li><font size="5">绩效管理</a></font></li>
            <li><a href="ViewTeacherPerformance">科研绩效管理</a></li>
            <li><a href="">教学绩效管理</a></li>
            <li><a href="">公益绩效管理</a></li>
            <li><a href="teacherjx.jsp">教师绩效查询</a></li>
          </ul>
		  <ul class="nav nav-sidebar">
            <li><font size="5">系统设置</a></font></li>
            <li><a href="viewAllScientificModular">科研设置</a></li>
            <li><a href="">公益设置</a></li>
			<li><a href="">教学设置</a></li>
            <li><a href="baseSet!getBaseSetInfo">基础设置</a></li>
          </ul>
		  <ul class="nav nav-sidebar">
            <li><font size="5">用户管理</a></font></li>
            <li><a href="add_Teacher">增加用户</a></li>
             <li><a href="update_Teacher!viewTeacher">修改用户</a></li>
          </ul>

        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">参加学术会议</h1>
        
<center>
      <br><br>
      <form action="" method="post" name="f0">
      <table class="table table-striped"  style="border-collapse:collapse">
		  <input type="submit" value="添加绩效"
				 onclick="document.f0.action='DoJAMeeting!admintmp?tmp1=0&tid=<%=id %>&teacherID=<%=teacherID%>';document.f0.submit();"></input>
      </form>
      <br>
      <form action="" method="post" name="f1">
     <table class="table table-striped"  style="border-collapse:collapse">
		<tr>
		   
			<td>工号</td>
			<td>姓名</td>
			<td>会议名称</td>
			<td>会议类别</td>
			<td>会议地点</td>
			<td>作者姓名</td>
			<td>作者身份</td>
			<td>论文题目</td>
			<td>论文检索情况</td>
			<td>得分</td>
			<td>操作</td>
			
		</tr>
		<%if(f!=null)for(int i=0;i<f.size();i++){ %>
			<tr>
				<td><%=f.get(i).getTeacherID() %></td>
				<td><%=pidao.getTeacherName(f.get(i).getTeacherID())%></td>
				<td><%=f.get(i).getAcaMeetName()%></td>
				<td><%=f.get(i).getMeetingTypeName() %></td>
				<td><%=f.get(i).getMeetingPlace() %></td>
				<td><%=f.get(i).getAuthorName() %></td>
				<td><%=f.get(i).getAuthorIdentity() %></td>
				<td><%=f.get(i).getPaperTitle() %></td>
				<td><%=f.get(i).getPRCondition() %></td>
				<td><%=f.get(i).getScore() %></td>


				<td><input type="submit" value="修改"
						   onclick="document.f1.action='DoJAMeeting!admintmp?tmp=<%=i%>&tid=<%=id %>&teacherID=<%=teacherID%>';document.f1.submit();window.location.reload(); "></input>
					<input type="submit" value="删除"
						   onclick="document.f1.action='DoJAMeeting!admindelJAMeeting?JoinAcaMID=<%=f.get(i).getJoinAcaMID()%>&TeacherID=<%=f.get(i).getTeacherID()%>';document.f1.submit();"></input>
				</td>
			</tr>
		<%} %>
	</table>
	</center>
	</form>
          <%if(g!=-1&&f!=null){%>
          <br>
		  <center>
          <form action="" method="post" name="f">
          <table class="table table-striped"  style="border-collapse:collapse">
			<tr><td>工号</td><td><input type="text" name="TeacherID" value=" <%=id %>"  readonly="true"></td></tr>
			<tr><td>姓名</td><td><input type="text" name="TeacherName" value="<%=pidao.getTeacherName(id) %>" readonly="true"></td></tr>
			<tr><td>ID</td><td><input type="text" name="JoinAcaMID" value="<%=f.get(g).getJoinAcaMID() %>"  readonly="true"/></td></tr>
			
			<tr><td>会议名称</td><td><input type="text" name="AcaMeetName" value="<%=f.get(g).getAcaMeetName() %>" /></td></tr>	
			<tr><td>会议类别</td><td>
			<select name="MeetingTypeName"  >
			<option value="<%=f.get(g).getMeetingTypeName()%>"  selected><%=f.get(g).getMeetingTypeName() %></option>
			     <%for(int i=0;i<b.size();i++){ %>
					<option value="<%=b.get(i)%>"  selected><%=b.get(i) %></option>
				<%} %>			
				 </select></td></tr>
			<tr><td>会议地点</td><td><select name="MeetingPlace"  >
			<option value="<%=f.get(g).getMeetingPlace()%>"  selected><%=f.get(g).getMeetingPlace() %></option>
			     <%for(int i=0;i<d.size();i++){ %>
					<option value="<%=d.get(i)%>"  selected><%=d.get(i) %></option>
				<%} %>			
				 </select>
				 </td></tr>
				 </table>
				 <table class="table table-striped"  style="border-collapse:collapse">
				 <tr><td>会议论文</td></tr>
				 <tr><td>作者姓名</td><td><input type="text" name="AuthorName" value="<%=f.get(g).getAuthorName() %>"/></td></tr>
				 <tr><td>作者身份</td><td><input type="text" name="AuthorIdentity" value="<%=f.get(g).getAuthorIdentity() %>"/></td></tr>
				 <tr><td>论文题目</td><td><input type="text" name="PaperTitle" value="<%=f.get(g).getPaperTitle()%>"/></td></tr>
				 
			<tr><td>检索情况</td>
			<td><select name="PRCondition"  >
			<option value="<%=f.get(g).getPRCondition()%>"  selected><%=f.get(g).getPRCondition() %></option>
			     <%for(int i=0;i<c.size();i++){ %>
					<option value="<%=c.get(i)%>"  selected><%=c.get(i) %></option>
				<%} %>			
				 </select>
			<tr><td>得分</td><td><input type="text"  name="FinalScore" value="<%=f.get(g).getScore() %>" /></td></tr>
					
				 
			</td></tr>
					 <tr>
						 <td>操作</td>
						 <td><input type="submit" value="提交"
									onclick="document.f.action='DoJAMeeting!adminupdateJAMeeting?teacherID=<%=id %>';document.f.submit();"></input>
						 </td>
					 </tr>
	</table>
	</form>
	</center>
	<%} %>	
	<%if(h!=-1){ %>
	<br>
	<center>
          <form action="" method="post" name="f2">
          <table class="table table-striped"  style="border-collapse:collapse">
			<tr><td>工号</td><td><input type="text" name="TeacherID" value="<%=id%>"  readonly="true"></td></tr>
			<tr><td>姓名</td><td><input type="text" name="TeacherName" value="<%=pidao.getTeacherName(id) %>" readonly="true"></td></tr>
			<tr><td>ID</td><td><input type="text" name="JoinAcaMID"/></td></tr>
			
			<tr><td>会议名称</td><td><input type="text" name="AcaMeetName" /></td></tr>	
			<tr><td>会议类别</td><td>
			<select name="MeetingTypeName"  >
			     <%for(int i=0;i<b.size();i++){ %>
					<option value="<%=b.get(i)%>"  selected><%=b.get(i) %></option>
				<%} %>			
				 </select></td></tr>
			<tr><td>会议地点</td><td><select name="MeetingPlace"  >
			     <%for(int i=0;i<d.size();i++){ %>
					<option value="<%=d.get(i)%>"  selected><%=d.get(i) %></option>
				<%} %>			
				 </select>
				 </td></tr>
				 </table>
				 <table class="table table-striped"  style="border-collapse:collapse">
				 会议论文
				 <tr><td>作者姓名</td><td><input type="text" name="AuthorName" /></td></tr>
				 <tr><td>作者身份</td><td><input type="text" name="AuthorIdentity" /></td></tr>
				 <tr><td>论文题目</td><td><input type="text" name="PaperTitle" /></td></tr>
				 
			<tr><td>检索情况</td>
			<td><select name="PRCondition"  >
			 <option value="无"  selected>无</option>
			     <%for(int i=0;i<c.size();i++){ %>
					<option value="<%=c.get(i)%>"  ><%=c.get(i) %></option>
				<%} %>			
				 </select>
					 <tr>
						 <td>操作</td>
						 <td><input type="submit" value="提交"
									onclick="document.f2.action='DoJAMeeting!adminAddJAMeeting?teacherID=<%=id%>';document.f2.submit();"></input>
						 </td>
					 </tr>
	</table>
	</form>
	</center>
<%} %>
	<s:debug></s:debug>
     
</body>
</html>