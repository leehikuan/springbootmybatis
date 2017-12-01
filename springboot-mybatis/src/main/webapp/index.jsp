<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test20</title>
<link href="css/bootstrap.css" rel="stylesheet">
<script src="js/jquery-3.2.1.js" type="text/javascript"></script>
<script src="js/bootstrap.js" type="text/javascript"></script>
</head>
<body>
	<%-- <h1>hello world!</h1>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>score</th>
				<th>age</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${people}" var="person" varStatus="status">
				<tr>
					<td><input name="score" type="text" class="form-control"
						value="${person.score}" disabled="disabled" /></td>
					<td><input name="age" type="text" class="form-control"
						value="${person.age}" disabled="disabled" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table> --%>
	<div class="container">
	<input type="text" hidden="true" name="path" value="${path}" id="pathId">
	<form class="form-horizontal" role="form" id="formId" action="uploadFile" method="post" enctype="multipart/form-data" style="margin-top:20px">
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">地址或经纬度所在列数</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" id="ColumnNum" name="ColumnNum"
					placeholder="请输入">
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">第一条数据所在行数</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" id="firstDataRowNum"
					name="firstDataRowNum" placeholder="请输入">
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">操作类型</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" id="type" name="type"
					placeholder="请输入">
			</div>
		</div>
		<div class="form-group ">
		<label for="inputfile" class="col-sm-2 control-label">文件输入</label>
		<div class=" col-sm-2">
			 <input type="file" id="inputfile" name="file">
		</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-2">
				<button type="button" class="btn btn-default" id="upload">提交</button>
			</div>
		</div>
	</form>
</div>
<div class="panel panel-success col-sm-offset-2 col-sm-2" hidden="true" id="changeDivId">
	<!-- <div class="panel-heading">
		<h3 class="panel-title ">面板标题</h3>
	</div> -->
	<div class="panel-body" >
		上传成功！点击<a id="changeId" href="#" >转换</a>进行转换
	</div>
</div>
<div class="panel panel-success col-sm-offset-2 col-sm-2" hidden="true" id="downloadDivId">
	<!-- <div class="panel-heading">
		<h3 class="panel-title ">面板标题</h3>
	</div> -->
	<div class="panel-body" >
		转换成功！点击<a id="downloadId" href="#" >下载</a>进行下载
	</div>
</div>
</body>
<script type="text/javascript">
//进行转换
$("#changeId").click(function(){
	var formData = new FormData($( "#formId" )[0]);
	var path=$("#pathId").val;
	formData.append("path",path);
	$.ajax({
        type: "post",
        url: "http://127.0.0.1:8080/program/change",
        data: formData,
        dataType: "text",
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,
        success: function(data){
        	$("#changeDivId").attr("hidden",false);
        }
    });
});
//进行下载
$("#downloadId").click(function(){
	$.ajax({
        type: "post",
        url: "http://127.0.0.1:8080/program/download",
        data: formData,
        dataType: "text",
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,
        success: function(data){
        	alert("下载成功！");
        }
    });
});
$("#upload").click(function(){
	var formData = new FormData($( "#formId" )[0]);
	$.ajax({
        type: "post",
        url: "http://127.0.0.1:8080/program/uploadFile",
        data: formData,
        dataType: "text",
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,
        success: function(data){
        	$("#changeDivId").attr("hidden",false);
        }
                   /*  $('#resText').empty();   //清空resText里面的所有内容
                    var html = ''; 
                    $.each(data, function(commentIndex, comment){
                          html += '<div class="comment"><h6>' + comment['username']
                                    + ':</h6><p class="para"' + comment['content']
                                    + '</p></div>';
                    });
                    $('#resText').html(html);*/
                 });
    });
	//$("#formId").submit();
</script>
</html>