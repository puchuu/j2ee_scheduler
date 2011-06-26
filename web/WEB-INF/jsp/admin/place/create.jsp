<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new place</title>
		<link rel="stylesheet" type="text/css" href="/static/css/global.css" />
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
		<script type="text/javascript" src="/static/scripts/upload.js"></script>
		<script type="text/javascript">
			$(document).ready(
			function() {
				$(".getPosition").click(
				function() {
					var popup_position = window.open("/view/position", "popup_position");
					popup_position.focus();

					var getPositionButton = $(this);
					$("html").bind(
					"position_accepted",
					function (event) {
						popup_position.close();
						var pos = event.pos;
						var namespace = getPositionButton.closest(".positionNamespace");
						namespace.find(".latitude").val(pos.latitude);
						namespace.find(".longitude").val(pos.longitude);

						$(this).unbind(event);
					}
				);
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
			}
		);
		</script>
    </head>
    <body>
		<%@include file="/WEB-INF/jsp/admin/global/head.jsp" %>
		<c:if test="${error == 1}">
			<p class="error">Invalid data</p>
		</c:if>
		<form action="/admin/place/create_action" method="post">
			<div>
				<span>Name</span>
				<input type="text" name="name" value="<c:out value="${param.name}" />" />
			</div>
			<div class="positionNamespace">
				<span>Position</span>
				<input readonly="readonly" type="text" name="latitude" class="latitude" size="16" value="<c:out value="${param.latitude}" />" />
				<input readonly="readonly" type="text" name="longitude" class="longitude" size="16" value="<c:out value="${param.longitude}" />" />
				<input type="button" class="getPosition" value="get" />
			</div>
			<div>
				<span>Image</span>
				<div class="image_uploader"></div>
				<img alt="" style="display: none; height: 100px;" src="<c:out value="${param.image}" />" />
				<input type="hidden" name="image" value="<c:out value="${param.image}" />" />
			</div>
			<input type="submit" name="submit" value="create" />
		</form>
    </body>
</html>
