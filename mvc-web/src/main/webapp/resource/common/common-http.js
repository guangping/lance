/*通用发送请求*/
var Service = Service || {};
Service = {
    /* 同步 */
    execute: function (url, params, callBack,method,dataType) {
        dataType=dataType || 'json';
        method=method || 'post';
        var data = jQuery.param(params || {});
        $.ajax({
            type: method,
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
    /*异步*/
    executeAsync: function (url, params, callBack) {
        var data = jQuery.param(params || {});
        // $.Loading.show();
        $.ajax({
            type: "post",
            url: url,
            data: data,
            async: true,
            dataType: 'json',
            success: function (result) {
                // $.Loading.hide();
                callBack(result);
            },
            error: function () {
                // $.Loading.hide();
                alert("操作失败，请重试");
            }
        });
    }
}