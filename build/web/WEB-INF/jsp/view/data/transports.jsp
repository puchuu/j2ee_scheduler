<%@page contentType="text/xml" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><?xml version="1.0" encoding="utf-8" ?>
<transports>
	<c:forEach var="transport" items="${transports}" >
		<transport id='<c:out value="${transport.id}" />'>
			<from>
				<latitude><c:out value="${transport.from_place.latitude}" /></latitude>
				<longitude><c:out value="${transport.from_place.longitude}" /></longitude>
			</from>
			<to>
				<latitude><c:out value="${transport.to_place.latitude}" /></latitude>
				<longitude><c:out value="${transport.to_place.longitude}" /></longitude>
			</to>
			<number><c:out value="${transport.number}" /></number>
			<type><c:out value="${transport.transport_type.name}" /></type>
			<start><c:out value="${transport.start}" /></start>
			<end><c:out value="${transport.end}" /></end>
			<spend><c:out value="${transport.spend}" /></spend>
			<period><c:out value="${transport.period}" /></period>
			<image><c:out value="${transport.image}" /></image>
		</transport>
	</c:forEach>
</transports>