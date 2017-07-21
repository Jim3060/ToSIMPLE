t<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String path2 = request.getContextPath();
	%>
</head>
<body>
	 <input class="form-control" name="username">
	 <button type="button"  id="commit">Commit</button>
	 <script src="<%=path2%>/questionnaire/js/jquery.min.js"></script>
	 <script src="<%=path2%>/questionnaire/js/jsencrypt.js"></script>
	 <script src="<%=path2%>/questionnaire/js/test.js"></script>
</body>
</html>