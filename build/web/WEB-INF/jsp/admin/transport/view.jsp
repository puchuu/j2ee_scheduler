<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View transport</title>
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
						From
					</th>
					<th>
						To
					</th>
					<th>
						Number
					</th>
					<th>
						Type
					</th>
					<th>
						Start
					</th>
					<th>
						End
					</th>
					<th>
						Spend
					</th>
					<th>
						Period
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
				<c:forEach var="transport" items="${transports}" >
					<tr>
						<td>
							<c:out value="${transport.id}" />
						</td>
						<td>
							<c:out value="${transport.from_place.name}" />
						</td>
						<td>
							<c:out value="${transport.to_place.name}" />
						</td>
						<td>
							<c:out value="${transport.number}" />
						</td>
						<td>
							<c:out value="${transport.transport_type.name}" />
						</td>
						<td>
							<c:out value="${transport.start}" />
						</td>
						<td>
							<c:out value="${transport.end}" />
						</td>
						<td>
							<c:out value="${transport.spend}" />
						</td>
						<td>
							<c:out value="${transport.period}" />
						</td>
						<td>
							<img style="height: 100px;" alt="" src="<c:out value="${transport.image}" />" />
						</td>
						<td>
							<a href="/admin/transport/change?id=<c:out value="${transport.id}" />">Change</a>
						</td>
						<td>
							<a href="/admin/transport/delete_action?id=<c:out value="${transport.id}" />">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>