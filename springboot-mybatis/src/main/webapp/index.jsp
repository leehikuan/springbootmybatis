<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Test20</title>
    <link href="css/bootstrap.css" rel="stylesheet" >
    <script src="js/jquery-3.2.1.js" type="text/javascript"></script>
	<script src="js/bootstrap.js" type="text/javascript"></script>
</head>
<body>
<h1> hello world!</h1>
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>score</th>
        <th>age</th>
      </tr>
    </thead>
    <tbody>
     <c:forEach items="${people}" var="person" varStatus="status" >
				<tr>
					<td >
						<input name="score" type="text" class="form-control" value="${person.score}" disabled="disabled" /> 
					</td>
					<td >
						<input name="age" type="text" class="form-control" value="${person.age}" disabled="disabled" /> 
					</td>
				</tr>
	</c:forEach>
    </tbody>
  </table>
	
</body>
</html>