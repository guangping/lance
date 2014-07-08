(function ($) {
    function create() {
        var loadding = $("#loading");
        if (loadding.size() == 0) {
            loadding = $("<div id=\"loading\" class=\"loading\" style=\"z-index:4000\"></div>").appendTo($("body")).hide();
        }
        return loadding;
    }

    $.Loading = $.Loading || {};
    $.Loading.show = function (text) {
        var loading = create();
        if (this.text) {
            loading.html(this.text);
        } else {
            if (text)
                loading.html(text);
        }
        $("<div style=\"height: 100%; width: 100%; position: fixed; left: 0pt; top: 0pt; z-index: 2999; opacity: 0.4;\" class=\"jqmOverlay\"></div>").appendTo($("body"));
        //loading.show();
    };
    $.Loading.hide = function () {
        create().hide();
        $(".jqmOverlay").remove();
    };
})(jQuery);


/*通用请求发送*/
var Service = Service || {};
Service = {
    /* 同步处理 */
    execute: function (url, params, callBack) {
        var data = jQuery.param(params || {});
        $.ajax({
            type: "post",
            url: url,
            data: data,
            async: false,
            dataType: 'json',
            success: function (result) {
                callBack(result);
            },
            error: function () {
                alert("操作失败，请重试");
            }
        });
    },
    executeAsync: function (url, params, callBack) {
        var data = jQuery.param(params || {});
        $.Loading.show();
        $.ajax({
            type: "post",
            url: url,
            data: data,
            async: true,
            dataType: 'json',
            success: function (result) {
                $.Loading.hide();
                callBack(result);
            },
            error: function () {
                $.Loading.hide();
                alert("操作失败，请重试");
            }
        });
    }
}
