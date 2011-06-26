<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change transport type</title>
		<link rel="stylesheet" type="text/css" href="/static/css/global.css" />
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
		<script type="text/javascript" src="/static/scripts/upload.js"></script>
		<script type="text/javascript">
			(function($) {
				$(document).ready(
				function() {
					$(".speed").keyup(
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
		<form action="/admin/transport_type/change_action" method="post">
			<input type="hidden" name="id" value="<c:out value="${transport_type.id}" />" />
			<div>
				<span>Name</span>
				<input type="text" name="name" value="<c:out value="${name}" />" />
			</div>
			<div>
				<span>Image</span>
				<div class="image_uploader"></div>
				<img alt="" style="display: none; height: 100px;" src="<c:out value="${image}" />" />
				<input type="hidden" name="image" value="<c:out value="${image}" />" />
			</div>
			<div>
				<span>Speed</span>
				<input type="text" class="speed" name="speed" value="<c:out value="${speed}" />" />
			</div>
			<input type="submit" name="submit" value="change" />
		</form>
    </body>
</html>
