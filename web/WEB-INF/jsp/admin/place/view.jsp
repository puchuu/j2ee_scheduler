<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/static/css/global.css" />
        <title>View places</title>
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
						Latitude
					</th>
					<th>
						Longitude
					</th>
					<th>
						Image
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
				<c:forEach var="place" items="${places}" >
					<tr>
						<td><c:out value="${place.id}" /></td>
						<td><c:out value="${place.name}" /></td>
						<td><c:out value="${place.latitude}" /></td>
						<td><c:out value="${place.longitude}" /></td>
						<td>
							<img style="height: 100px;" alt="" src="<c:out value="${place.image}" />" />
						</td>
						<td>
							<a href="/admin/place/change?id=<c:out value="${place.id}" />">Change</a>
						</td>
						<td>
							<a href="/admin/place/delete_action?id=<c:out value="${place.id}" />">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
    </body>
</html>
