<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin login page</title>
		<link rel="stylesheet" type="text/css" href="/static/css/global.css" />
    </head>
    <body>
		<form action="/admin/login_action" method="post">
			<div>
				<span>login:</span>
				<input type="text" name="login" />
			</div>
			<div>
				<span>password:</span>
				<input type="password" name="password" />
			</div>
			<input type="submit" name="submit" value="login" />
		</form>
    </body>
</html>
