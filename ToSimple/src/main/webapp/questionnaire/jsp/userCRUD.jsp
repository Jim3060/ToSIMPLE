<%@ page import="java.util.ArrayList"%>
<%@ page import="model.User"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Questionnaire</title>

<%
	String path = request.getContextPath();
%>
</head>

<body>
	
	
	<table class="table table-striped table-bordered table-hover"
		id="dataTables">
		<thead>
			<tr>
			    <th>ID</th>
				<th>Username</th>
				<th>Password</th>
				<th>Email</th>
				<th>Role</th>
				
			</tr>
		</thead>
		
		<s:iterator id="u" value="users">
		
		<tbody>
			
			<tr>
				<td><s:property value="#u.id" /></td>
			    <td><s:property value="#u.userName" /></td>
			    <td><s:property value="#u.password" /></td>
			    <td><s:property value="#u.email" /></td>
			    <td><s:property value="#u.role" /></td>
				
			</tr>
			
		</tbody>
		</s:iterator>
	</table>
							

	

</body>

</html>

