<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new admin</title>
		<link rel="stylesheet" type="text/css" href="/static/css/global.css" />
    </head>
    <body>
		<%@include file="/WEB-INF/jsp/admin/global/head.jsp" %>
		<c:if test="${error == 1}">
			<p class="error">Invalid data</p>
		</c:if>
		<form action="/admin/create_action" method="post">
			<div>
				<span>Email</span>
				<input type="text" name="email" value="<c:out value="${param.email}" />" />
			</div>
			<div>
				<span>Name</span>
				<input type="text" name="name" value="<c:out value="${param.name}" />" />
			</div>
			<div>
				<span>Login</span>
				<input type="text" name="login" value="<c:out value="${param.login}" />" />
			</div>
			<div>
				<span>Password</span>
				<input type="password" name="password" />
			</div>
			<input type="submit" name="submit" value="create" />
		</form>
    </body>
</html>
