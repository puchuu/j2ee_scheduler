<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<title></title>

		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
		<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript" src="/static/scripts/init.js"></script>
		<script type="text/javascript" src="/static/scripts/transports.js"></script>

		<link rel="stylesheet" type="text/css" href="/static/css/styles.css" />
	</head>
	<body>
		<div id="google_map" style="width: 100%; height: 100%;"></div>
		<div id="infoWindowContentTemplate" style="display: none;">
			<div class="infoWindowContent">
				<div class="voyage">
					<label>рейс:</label>
					<span></span>
					<br clear="all" />
				</div>
				<div class="place_name">
					<label>остнаовка:</label>
					<span></span>
					<br clear="all" />
				</div>
				<div class="start">
					<label>начало:</label>
					<span></span>
					<br clear="all" />
				</div>
				<div class="end">
					<label>конец:</label>
					<span></span>
					<br clear="all" />
				</div>
				<div class="spend">
					<label>в пути:</label>
					<span></span>
					<br clear="all" />
				</div>
				<div class="period">
					<label>ждать:</label>
					<span></span>
					<br clear="all" />
				</div>
				<div class="image" style="height: 100px;">
					<img alt="" style="height: 100px;">
				</div>
			</div>
		</div>
	</body>
</html>
