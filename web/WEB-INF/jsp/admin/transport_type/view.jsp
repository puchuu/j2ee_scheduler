<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View transport type</title>
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
						Name
					</th>
					<th>
						Image
					</th>
					<th>
						Speed
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
				<c:forEach var="transport_type" items="${transport_types}" >
					<tr>
						<td><c:out value="${transport_type.id}" /></td>
						<td><c:out value="${transport_type.name}" /></td>
						<td>
							<img style="height: 100px;" alt="" src="<c:out value="${transport_type.image}" />" />
						</td>
						<td><c:out value="${transport_type.speed}" /></td>
						<td>
							<a href="/admin/transport_type/change?id=<c:out value="${transport_type.id}" />">Change</a>
						</td>
						<td>
							<a href="/admin/transport_type/delete_action?id=<c:out value="${transport_type.id}" />">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
    </body>
</html>
