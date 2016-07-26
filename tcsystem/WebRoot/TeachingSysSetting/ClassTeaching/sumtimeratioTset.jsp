<%@page import="com.nuaa.ec.teaching.model.TFClassTeach_sumtime"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int addres = 100;
    if (request.getAttribute("resu") != null)
	addres = (Integer)request.getAttribute("resu");
%>
<%
	List<TFClassTeach_sumtime> ctstdao = (List)request.getAttribute("classteachtime");
  %>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html lang="zh-CN">

  <head>
  <base target="_self"> 
    </head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>classteachingset</title>
    
    <link rel="shortcut icon" href="favicon.ico"> <link href="css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    
    <!-- Sweet Alert -->
    <link href="css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

    <!-- Data Tables -->
    <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    <script type="text/javascript">
    	function assignment(a,b,c) {
    		    document.getElementById("upsumTimeID").value=a;    		    
    		    document.getElementById("upsumTime").value=b;
    		    document.getElementById("upratio").value=c;
		}
    	
    	function confirmdel(id) {
			if(confirm("确定要删除吗？"))
				window.location.replace("TsetClassTeaching_sumtimeratio!deleteclassteachtime?ID="+id);
			else
				window.location.replace("#");
		}
    	
    	function DoCheck() {
    		var addres = <%=addres %>;
    		//alert(addres);
			switch (addres){
				case 0:alert("operate fail !!!");
				break;
				case 1:alert("add success!");
				break;
				case 2:alert("update success!");
				break;
				case 3:alert("delete success !!!");
				break;
				default: break;
			}
		}
    </script>
</head>

<body class="gray-bg"  onload="DoCheck()">

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>课堂教学个人总站堂时数 <small></small></h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="table_data_tables.html#">选项1</a>
                                </li>
                                <li><a href="table_data_tables.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    
                    <div class="">
                         <button class="btn  btn-primary" type="submit" data-backdrop="true" data-toggle="modal" data-target="#add">
                         <strong>添加</strong>
                         </button>
                            
                        </div>
                        <div class="example">
                        <form method="post" name="f">
                       <table  id="tb" class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                                <tr>
									<td>总站堂时ID</td>
									<td>总站堂时范围</td>
									<td>占系数比</td>
									<td>Operate</td>
								</tr>
                            </thead>
                            <tbody>
                                 <% 
                                 if(ctstdao != null)
                                	 for(int i=0;i<ctstdao.size();i++){
                                 %>
                                 <tr>
                                 	<td><%=ctstdao.get(i).getSumtimeID() %></td>
                                 	<td><%=ctstdao.get(i).getSumtime() %></td>
                                 	<td><%=ctstdao.get(i).getRatio() %></td>
                                 	<td>
                                 		 <a    class="btn btn-primary btn-sm" data-toggle="modal"  onclick="assignment('<%=ctstdao.get(i).getSumtimeID() %>','<%=ctstdao.get(i).getSumtime() %>','<%=ctstdao.get(i).getRatio() %>')" data-target="#update" >修改</a>
			                             <a   class="btn btn-primary btn-sm del"  onclick="confirmdel('<%=ctstdao.get(i).getSumtimeID() %>')">删除</a>
                                 	</td>
                                 </tr>
                                 <%} %>
                            </tbody>                           
                        </table>
                        </form>
                   </div>
                </div>
            </div>
        </div>
    </div>   
   <div id="update" class="modal fade" aria-hidden="true"tabindex="-1" role="dialog"     aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                            <h3 class="m-t-none m-b">add</h3>
                            <form role="form" id="onlyForm" name="updatesumtime" action="TsetClassTeaching_sumtimeratio!updateclassteachtime">
                            
                                <div class="form-group">
                                	<label>总站堂时ID:</label>                                	
									<input id="upsumTimeID" type="text"  class="form-control" name="upsumTimeID" value="" readonly="readonly">
                                </div>
                                <div class="form-group">                                
                                    <label>总站堂时范围:</label>
                                    <input id="upsumTime" type="text"  class="form-control" name="upsumTime" value="">
                                </div>   
                                <div class="form-group">                                
                                    <label>占系数比:</label>
                                    <input id="upratio" type="text"  class="form-control" name="upratio" value="">
                                </div>                                                          
                                <div>
                                    <button type="button"   class="btn btn-outline btn-primary pull-right m-t-n-xs" data-dismiss="modal">关闭</button>
                                    <button  class="btn  btn-primary pull-left m-t-n-xs "  type="submit">
                                     <i class="fa fa-check"></i>
                                    <strong>提交</strong>
                                    </button	>
                               </div>
                            </form>
                    </div>
                </div>
            </div>
        </div>
    </div>              
    
    <div id="add" class="modal fade" aria-hidden="true"tabindex="-1" role="dialog"     aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                            <h3 class="m-t-none m-b">add</h3>
                        <form role="form" id="onlyForm11" name="addsumtime"
                              action="TsetClassTeaching_sumtimeratio!addclassteachtime">
                                <div class="form-group">                                
                                    <label>总站堂时范围:</label>
                                    <input id="sumTime" type="text"  class="form-control" name="sumTime" value="">
                                </div>   
                                <div class="form-group">                                
                                    <label>占系数比:</label>
                                    <input id="addratio" type="text"  class="form-control" name="addratio" value="">
                                </div>                                                          
                                <div>
                                    <button type="button"   class="btn btn-outline btn-primary pull-right m-t-n-xs" data-dismiss="modal">关闭</button>
                                    <button  class="btn  btn-primary pull-left m-t-n-xs "  type="submit">
                                     <i class="fa fa-check"></i>
                                    <strong>提交</strong>
                                    </button	>
                               </div>
                            </form>
                    </div>
                </div>
            </div>
        </div>
    </div>              
    
    <script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.5"></script>
    <script src="js/plugins/jeditable/jquery.jeditable.js"></script>
    <script src="js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <script src="js/content.min.js?v=1.0.0"></script>
    <script src="js/plugins/iCheck/icheck.min.js"></script>
    <script src="js/plugins/sweetalert/sweetalert.min.js"></script>
    <script>
        $(document).ready(function () {
            $(".dataTables-example").dataTable();
            var oTable = $("#editable").dataTable();
            oTable.$("td").editable("../example_ajax.php", {
                "callback": function (sValue, y) {
                    var aPos = oTable.fnGetPosition(this);
                    oTable.fnUpdate(sValue, aPos[0], aPos[1])
                }, "submitdata": function (value, settings) {
                    return {"row_id": this.parentNode.getAttribute("id"), "column": oTable.fnGetPosition(this)[2]}
                }, "width": "90%", "height": "100%"
            })
        });
        function fnClickAddRow() {
            $("#editable").dataTable().fnAddData(["Custom row", "New row", "New row", "New row", "New row"])
        }
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});            
    </script>
    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
	<s:debug></s:debug>
     
</body>
</html>