<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View admins</title>
		<link rel="stylesheet" type="text/css" href="/static/css/global.css" />
    </head>
    <body>
		<%@include file="/WEB-INF/jsp/admin/global/head.jsp" %>
		<table border="1" cellpadding="10" cellspacing="0">
			<thead>
				<tr>
					<th>
						Id
					</th>
					<th>
						Email
					</th>
					<th>
						Name
					</th>
					<th>
						Login
					</th>
					<th>
						Password
					</th>
					<th>
						Salt
					</th>
					<th>
						Change
					</th>
					<th>
						Delete
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="admin" items="${admins}" >
					<tr>
						<td><c:out value="${admin.id}" /></td>
						<td><c:out value="${admin.email}" /></td>
						<td><c:out value="${admin.name}" /></td>
						<td><c:out value="${admin.login}" /></td>
						<td><c:out value="${admin.password}" /></td>
						<td><c:out value="${admin.salt}" /></td>
						<td>
							<a href="/admin/change?id=<c:out value="${admin.id}" />">Change</a>
						</td>
						<td>
							<a href="/admin/delete_action?id=<c:out value="${admin.id}" />">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
    </body>
</html>
