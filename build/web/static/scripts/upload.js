(function($) {
	$.fn.upload = function(options) {
		function getRealHeight(obj) {
			var height = $(obj).css("height");
			var iheight = $(obj).innerHeight();
			if(height.indexOf("px") > 0) {
				if(Number(height.substring(0, height.indexOf("px"))) > iheight) {
					return iheight;
				} else {
					return Number(height.substring(0, height.indexOf("px")));
				}
			} else {
				return iheight;
			}
		}

		function getRealWidth(obj) {
			var width = $(obj).css("width");
			var iwidth = $(obj).innerWidth();
			if(width.indexOf("px") > 0) {
				if(Number(width.substring(0, width.indexOf("px"))) > iwidth) {
					return iwidth;
				} else {
					return Number(width.substring(0, width.indexOf("px")));
				}
			} else {
				return iwidth;
			}
		}

		var obj = this;
		var offset = $(this).offset();
		var width = getRealWidth($(this));
		var height = getRealHeight($(this));
		if(options.left) {
			var left = options.left;
		} else {
			var left = offset.left;
		}
		if(options.top) {
			var top = options.top;
		} else {
			var top = offset.top;
		}

		function createIframe() {
			$("iframe.iframeFileHidden").remove();

			loadWindow = $("<iframe scrolling=\"no\" style=\"opacity: 0; position: absolute; z-index: 9999; left: " + left + "px; top: " + top + "px; border-style: none; overflow: hidden;\" class=\"iframeFileHidden\" width=\"" + width + "\" height=\"" + height + "\"></iframe>");
			if($.browser.msie) {
				$(loadWindow).css("filter", "alpha(opacity=0)");
			}
			loadWindow.insertBefore($("body").children().first());
			var loadWindow = loadWindow[loadWindow.length - 1].contentWindow || frame[loadWindow.length - 1];

			data = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
			data += "<html><head></head><body style=\"margin: 0; padding: 0;\">";
			data += "<form method=\"" + options.method + "\" action=\"" + options.action + "\" style=\"margin: 0; padding: 0;\" class=\"formFileHidden\" enctype=\"" + options.enctype + "\">";
			data += "<a class=\"aFileHidden\" style=\"display: block; overflow: hidden; width: " + width + "px;\">";
			data += "<input type=\"hidden\" name=\"UPLOAD_IDENTIFIER\" value=\"" + options.uploadId + "\" />";
			data += "<input name=\"" + options.name + "\" accept=\"" + options.accept +"\" class=\"inputFileHidden\" style=\"cursor: pointer; display: block; margin: 0; padding: 0; border-style: none; height: " + height * 2 +"px; font-size: 99px; font-family: Arial; margin: -10px 0 0 -1220px;\" type=\"file\" />";
			data += "</a>";
			data += "</form>";
			data += "</body></html>";

			loadWindow.document.open();
			loadWindow.document.write(data);
			loadWindow.document.close();

			var contents = $("iframe.iframeFileHidden").contents();

			var iframe = $("iframe.iframeFileHidden");

			function getContentsAndReload() {
				if($.browser.msie || $.browser.opera || $.browser.safari) {
					endLoading = (loadIndex > 0);
				} else {
					endLoading = (loadIndex > 1);
				}
				if(endLoading) {
					$(iframe).css("display", "none");
					var response;
					if ($(iframe).contents().find("body").children().size() == 0 || ($(iframe).contents().find("body").children().size() == 1 && $(iframe).contents().find("body").children().get(0).tagName.toLowerCase() == "pre")) {
						response = $(iframe).contents().text();
					} else {
						if($(iframe).contents().find("html").get(0).outerHTML) {
							response = $(iframe).contents().find("html").get(0).outerHTML;
						} else {
							response = new XMLSerializer().serializeToString($(iframe).contents().find("html").get(0));
						}
					}
					if($.isFunction(options.onComplete)) {
						options.onComplete.call(obj, response);
					}
					createIframe();
				} else {
					loadIndex++;
				}
			}

			var loadIndex = 0;
			if($.browser.opera) {
				$(iframe).load(getContentsAndReload);
			} else {
				$(iframe).ready(getContentsAndReload);
				$(iframe).load(getContentsAndReload);
			}

			$(contents).find("input.inputFileHidden").change(
				function() {
					if($.isFunction(options.beforeSend)) {
						options.beforeSend.call(obj);
					}
					$(contents).find("form.formFileHidden").submit();
				}
				);
		}

		createIframe();

		return this;
	}
})(jQuery);