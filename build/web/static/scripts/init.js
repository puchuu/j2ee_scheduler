(function ($) {

	$(document).ready(
		function() {
			$.ajax(
			{
				type:     "get",
				url:      "/static/data/options.xml",
				dataType: "xml",
				async:    true,
				success:  function(xml) {
					geolocation(
						function(center) {
							try {
								init.call(this, $(xml).children("map"), center);
							} catch(error) {
								console.log(error);
							}
						}
						);
				}
			}
			);
		}
		);

	function geolocation(handler) {
		if(navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(
				function(position) {
					var map_position = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
					if($.isFunction(handler)) {
						handler.call(this, map_position);
					}
				},
				function() {
					if($.isFunction(handler)) {
						handler.call(this, false);
					}
				}
				);
		} else if (google.gears) {
			var geo = google.gears.factory.create("beta.geolocation");
			geo.getCurrentPosition(
				function(position) {
					var map_position = new google.maps.LatLng(position.latitude, position.longitude);
					if($.isFunction(handler)) {
						handler.call(this, map_position);
					}
				}, function() {
					if($.isFunction(handler)) {
						handler.call(this, false);
					}
				}
				);
		} else {
			if($.isFunction(handler)) {
				handler.call(this, false);
			}
		}
	}

	function googleMapGetProperty(property) {
		if(property == undefined) {
			var current_text = $.trim($(this).text()).toString();
			if(current_text.length == 0) {
				return null;
			} else {
				return current_text;
			}
		} else {
			var current_text = $.trim($(this).attr(property)).toString();
			if(current_text.length == 0) {
				return null;
			} else {
				return current_text;
			}
		}
	}

	$.fn.extend(
	{
		googleMapGetProperty: googleMapGetProperty
	}
	);

	function init(options, center) {
		var apply_settings = {};

		apply_settings.backgroundColor = options.children("backgroundColor").googleMapGetProperty();
		var center_option              = options.children("center");
		if(center != false) {
			apply_settings.center = center;
		} else {
			apply_settings.center = new google.maps.LatLng(
				Number(center_option.children("latitude").googleMapGetProperty()),
				Number(center_option.children("longitude").googleMapGetProperty())
				);
		}

		apply_settings.disableDefaultUI       = options.children("disableDefaultUI").googleMapGetProperty() == 1;
		apply_settings.disableDoubleClickZoom = options.children("disableDoubleClickZoom").googleMapGetProperty() == 1;
		apply_settings.draggable              = options.children("draggable").googleMapGetProperty() == 1;
		apply_settings.draggableCursor        = options.children("draggableCursor").googleMapGetProperty();
		apply_settings.draggingCursor         = options.children("draggingCursor").googleMapGetProperty();
		apply_settings.keyboardShortcuts      = options.children("keyboardShortcuts").googleMapGetProperty();
		apply_settings.mapTypeControl         = options.children("mapTypeControl").googleMapGetProperty() == 1;

		var mapTypeControlOptions_option = options.children("mapTypeControlOptions");
		var mapTypeIds_option = mapTypeControlOptions_option.children("mapTypeIds");
		var mapTypeIds = [];
		mapTypeIds_option.children("mapTypeId").each(
			function(index) {
				mapTypeIds[index] = google.maps.MapTypeId[$(this).googleMapGetProperty()];
			}
			);
		apply_settings.mapTypeControlOptions = {
			mapTypeIds: mapTypeIds,
			position:  google.maps.ControlPosition[mapTypeControlOptions_option.children("position").googleMapGetProperty()],
			style:     google.maps.MapTypeControlStyle[mapTypeControlOptions_option.children("style").googleMapGetProperty()]
		};
		apply_settings.mapTypeId  = google.maps.MapTypeId[options.children("mapTypeId").googleMapGetProperty()];
		apply_settings.maxZoom    = Number(options.children("maxZoom").googleMapGetProperty());
		apply_settings.minZoom    = Number(options.children("minZoom").googleMapGetProperty());
		apply_settings.noClear    = options.children("noClear").googleMapGetProperty() == 1;
		apply_settings.panControl = options.children("panControl").googleMapGetProperty() == 1;

		var panControlOptions_option = options.children("panControlOptions");
		apply_settings.panControlOptions = {
			position: google.maps.ControlPosition[panControlOptions_option.children("position").googleMapGetProperty()]
		};
		apply_settings.scaleControl = options.children("scaleControl").googleMapGetProperty() == 1;

		var scaleControlOptions_option = options.children("scaleControlOptions");
		apply_settings.scaleControlOptions = {
			position: google.maps.ControlPosition[scaleControlOptions_option.children("position").googleMapGetProperty()],
			style:    google.maps.ScaleControlStyle[scaleControlOptions_option.children("style").googleMapGetProperty()]
		};

		apply_settings.scrollwheel = options.children("scrollwheel").googleMapGetProperty() == 1;

		apply_settings.zoom = Number(options.children("zoom").googleMapGetProperty());
		apply_settings.zoomControl = options.children("zoomControl").googleMapGetProperty();
		var zoomControlOptions_option = options.children("zoomControlOptions");
		apply_settings.zoomControlOptions = {
			position: google.maps.ControlPosition[zoomControlOptions_option.children("position").googleMapGetProperty()],
			style:    google.maps.ZoomControlStyle[zoomControlOptions_option.children("style").googleMapGetProperty()]
		};

		window.google_map = new google.maps.Map($("#google_map").get(0), apply_settings);
		window.google_directionsService = new google.maps.DirectionsService();
		window.google_directionsDisplay = [];

		$("#google_map").trigger("initialized");
	}
})(jQuery)