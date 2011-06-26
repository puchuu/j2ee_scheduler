<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change transport</title>
		<link rel="stylesheet" type="text/css" href="/static/css/global.css" />
		<link rel="stylesheet" type="text/css" href="/static/css/jquery-ui-timepicker-addon.css" />
		<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" />
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/static/scripts/jquery-ui-timepicker-addon.js"></script>
		<script type="text/javascript" src="/static/scripts/upload.js"></script>
		<script type="text/javascript">
			(function($) {
				$(document).ready(
				function() {
					$(".from_place_id").change(
					function() {
						$(".to_place_id option[disabled='disabled']").removeAttr("disabled");
						$(".to_place_id option[value='" + $(this).val() + "']").attr("disabled", "disabled");
					});
					$(".to_place_id").change(
					function() {
						$(".from_place_id option[disabled='disabled']").removeAttr("disabled");
						$(".from_place_id option[value='" + $(this).val() + "']").attr("disabled", "disabled");
					});
					
					$(".start, .end, .spend, .period").timepicker({
						showHour: true,
						showMinute: true,
						showSecond: true,
						timeFormat: "hh:mm:ss"
					});
					
					$(".number").keyup(
					function() {
						var val = $(this).val();
						if(!/^\d$/.test(val.charAt(val.length - 1))) {
							$(this).val(val.substr(0, val.length - 1))
						}
					});
					
					$(".image_uploader").each(
					function() {
						if($.trim($(this).parent().find("input").val()) != "") {
							$(this).parent().find("img").show();
						}
						$(this).upload({
							action: "/admin/image_upload",
							method: "post",
							enctype: "multipart/form-data",
							name: "image",
							accept: "image/png,image/jpeg,image/gif",
							beforeSend: function() {
								$(this).css("backgroundImage", "url(/static/images/uploader.gif)");
							},
							onComplete: function(data) {
								$(this).css("backgroundImage", "");
								data = $.parseJSON(data);
								if(data.file_name == undefined) {
									alert("such image not accepted");
								} else {
									$(this).parent().find("img").attr("src", "/static/upload/" + data.file_name).show();
									$(this).parent().find("input").val("/static/upload/" + data.file_name);
								}
							}
						});
					});
				});
			})(jQuery);
		</script>
    </head>
    <body>
		<%@include file="/WEB-INF/jsp/admin/global/head.jsp" %>
		<c:if test="${error == 1}">
			<p class="error">Invalid data</p>
		</c:if>
		<form action="/admin/transport/change_action" method="post">
			<input type="hidden" name="id" value="<c:out value="${transport.id}" />" />
			<div>
				<span>From</span>
				<select name="from_place_id" class="from_place_id">
					<c:forEach var="place" items="${places}" varStatus="status">
						<c:choose>
							<c:when test="${place.id == from_place_id}">
								<option selected="selected" value="<c:out value="${place.id}" />">
									<c:out value="${place.name}" />
								</option>
							</c:when>
							<c:when test="${place.id == to_place_id}">
								<option disabled="disabled" value="<c:out value="${place.id}" />">
									<c:out value="${place.name}" />
								</option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value="${place.id}" />">
									<c:out value="${place.name}" />
								</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>

			<div>
				<span>To</span>
				<c:if test="${fn:length(places) > 1}">
					<select name="to_place_id" class="to_place_id">
						<c:forEach var="place" items="${places}" varStatus="status">
							<c:choose>
								<c:when test="${place.id == from_place_id}">
									<option disabled="disabled" value="<c:out value="${place.id}" />">
										<c:out value="${place.name}" />
									</option>
								</c:when>
								<c:when test="${place.id == to_place_id}">
									<option selected="selected" value="<c:out value="${place.id}" />">
										<c:out value="${place.name}" />
									</option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value="${place.id}" />">
										<c:out value="${place.name}" />
									</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</c:if>
			</div>

			<div>
				<span>Start</span>
				<input class="start" type="text" name="start" value="<c:out value="${start}" />" />
			</div>
			<div>
				<span>End</span>
				<input class="end" type="text" name="end" value="<c:out value="${end}" />" />
			</div>
			<div>
				<span>Spend</span>
				<input class="spend" type="text" name="spend" value="<c:out value="${spend}" />" />
			</div>
			<div>
				<span>Period</span>
				<input class="period" type="text" name="period" value="<c:out value="${period}" />" />
			</div>
			<div>
				<span>Image</span>
				<div class="image_uploader"></div>
				<img alt="" style="display: none; height: 100px;" src="<c:out value="${image}" />" />
				<input type="hidden" name="image" value="<c:out value="${image}" />" />
			</div>

			<div>
				<span>Type</span>
				<select name="transport_type_id">
					<c:forEach var="transport_type" items="${transport_types}" varStatus="status">
						<c:choose>
							<c:when test="${transport_type.id == transport_type_id}">
								<option selected="selected" value="<c:out value="${transport_type.id}" />">
									<c:out value="${transport_type.name}" />
								</option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value="${transport_type.id}" />">
									<c:out value="${transport_type.name}" />
								</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>

			<div>
				<span>Number</span>
				<input class="number" type="text" name="number" value="<c:out value="${number}" />" />
			</div>

			<input type="submit" name="submit" value="change" />
		</form>
    </body>
</html>
