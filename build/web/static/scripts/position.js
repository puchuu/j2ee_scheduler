(function ($) {
	$(document).ready(
		function() {
			$("#google_map").bind(
				"initialized",
				function() {
					var uri = parseUri(window.location);
					var lat = uri.queryKey.latitude;
					var lng = uri.queryKey.longitude;
					if(lat && lng) {
						window.pos_marker = new google.maps.Marker(
						{
							position:   new google.maps.LatLng(Number(lat), Number(lng)),
							map:        window.google_map,
							draggable:  true
						}
						);

						google.maps.event.addListener(
							window.pos_marker,
							"click",
							function() {
								if(confirm("Do you accept this position?")) {
									sendPosition(window.pos_marker.getPosition());
								}
							}
							);
					}

					google.maps.event.addListener(
						window.google_map,
						"click",
						function(event) {
							if(window.pos_marker == undefined) {
								window.pos_marker = new google.maps.Marker(
								{
									position:   event.latLng,
									map:        window.google_map,
									draggable:  true
								}
								);

								google.maps.event.addListener(
									window.pos_marker,
									"click",
									function() {
										if(confirm("Do you accept this position?")) {
											sendPosition(window.pos_marker.getPosition());
										}
									}
									);
							}
						}
						);
				}
				);
		}
		);

	function sendPosition(pos) {
		var event = $.Event("position_accepted");
		event.pos = {
			latitude:  pos.lat(),
			longitude: pos.lng()
		};
		if(window.opener.$ != undefined) {
			window.opener.$("html").trigger(event);
		}
	}
})(jQuery)