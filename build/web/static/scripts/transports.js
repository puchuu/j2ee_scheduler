(function ($) {
	$(document).ready(
		function() {
			$("#google_map").bind(
				"initialized",
				function() {
					$.ajax(
					{
						type:     "get",
						url:      "/view/data/transports",
						dataType: "xml",
						async:    true,
						success:  function(xml) {
							setTransports.call(this, $(xml).find("transports"));
						}
					}
					);
				}
				);
		}
		);

	function setTransports(transports) {
		transports.children("transport").each(
			function() {
				var id = Number($(this).googleMapGetProperty("id"));

				var from_option = $(this).children("from");
				var from = new google.maps.LatLng(
					Number(from_option.children("latitude").googleMapGetProperty()),
					Number(from_option.children("longitude").googleMapGetProperty())
					);

				var to_option = $(this).children("to");
				var to = new google.maps.LatLng(
					Number(to_option.children("latitude").googleMapGetProperty()),
					Number(to_option.children("longitude").googleMapGetProperty())
					);

				var infoWindow = new google.maps.InfoWindow();

				var number = $(this).children("number").googleMapGetProperty();
				var type = $(this).children("type").googleMapGetProperty();
				var start = $(this).children("start").googleMapGetProperty();
				var end = $(this).children("end").googleMapGetProperty();
				var spend = $(this).children("spend").googleMapGetProperty();
				var period = $(this).children("period").googleMapGetProperty();
				var image = $(this).children("image").googleMapGetProperty();

				google.maps.event.addListenerOnce(
					infoWindow,
					"content_changed",
					function() {
						setInfoWindowContent.call(this, number, type, start, end, spend, period, image);
					}
					);

				var request = {
					origin:      from,
					destination: to,
					travelMode:  google.maps.DirectionsTravelMode.DRIVING
				};
				window.google_directionsService.route(
					request,
					function(result, status) {
						if (status == google.maps.DirectionsStatus.OK) {
							window.google_directionsDisplay[id] = new google.maps.DirectionsRenderer(
							{
								directions: result,
								map: window.google_map,
								preserveViewport: true,
								infoWindow: infoWindow
							}
							);
						}
					}
					);
			}
			);
	}

	function setInfoWindowContent(number, type, start, end, spend, period, image) {
		var place_name;
		if($(this.content).find(".infoWindowContent").size() == 0) {
			place_name = $.trim($(this.content).html());
			$(this.content).data("place_name", place_name);
		} else {
			place_name = $(this.content).data("place_name");
		}

		var infoWindowContent = $("#infoWindowContentTemplate").clone(true);
		infoWindowContent.find(".voyage").find("span").html(type + " #" + number);
		infoWindowContent.find(".place_name").find("span").html(place_name);
		infoWindowContent.find(".start").find("span").html(start);
		infoWindowContent.find(".end").find("span").html(end);
		infoWindowContent.find(".spend").find("span").html(spend);
		infoWindowContent.find(".period").find("span").html(period);
		infoWindowContent.find(".image").find("img").attr("src", image);
		this.setContent(infoWindowContent.html());

		google.maps.event.addListenerOnce(
			this,
			"content_changed",
			function() {
				setInfoWindowContent.call(this, number, type, start, end, spend, period, image);
			}
			);
	}
})(jQuery)