// create by gaobo 2016年03月28日
;(function(){
    // This lib will inject a WebViewJavascriptBridge Object to window object. So in your js, before use WebViewJavascriptBridge,
    // you must detect if WebViewJavascriptBridge exist. If WebViewJavascriptBridge does not exit, you can listen to
    // WebViewJavascriptBridgeReady event, as the blow code shows:
    function connectWebViewJavascriptBridge(callback) {
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge)
        } else {
            document.addEventListener(
                'WebViewJavascriptBridgeReady'
                , function () {
                    callback(WebViewJavascriptBridge)
                },
                false
            );
        }
    }

    connectWebViewJavascriptBridge(function (bridge) {
        // You can also define a default handler use init method, so that Java can send message to js without assigned handlerName
        bridge.init(function (message, responseCallback) {
            alert('JS got a message', message);
            console.log('JS got a message', message);
            var data = {
                'Javascript Responds': '测试中文!'
            };
            console.log('JS responding with', data);
            responseCallback(data);
        });

        // Register a Java handler function so that js can call
        bridge.registerHandler("functionInJs", function (data, responseCallback) {
            document.getElementById("show").innerHTML = ("data from Java: = " + data);
            var responseData = "Javascript Says Right back aka!";
            responseCallback(responseData);
        });

        // 定义NJBridge类
        var nj = window.NJBridge = {
            njBridge: bridge,
            exec:function(success, error, service, action, args) {
                window.WebViewJavascriptBridge.callHandler(
                    service
                    , {'actionName': action, 'arguments': args}
                    , function (responseData) {
                        var respJO = val(responseData);
                        if (equals(respJO.callbackName, "success")) {
                            success(respJO.data);
                        } else {
                            error(respJO.data);
                        }
                    }
                );
            }
        };

        var deviceReadyEvent = document.createEvent("Events");
        deviceReadyEvent.initEvent("deviceready");
        deviceReadyEvent.nj = nj;
        document.dispatchEvent(deviceReadyEvent);
    });
})();