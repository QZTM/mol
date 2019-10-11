function getRunEnv(){
    var device = layui.device("dingtalk");
    alert(device);
    console.log(device);

    /*电脑浏览器打开：*/
    /*  android: false
        dingtalk: false
        ie: false
        ios: false
        os: "windows"
        weixin: false
    */

    /*安卓钉钉打开：*/
    /*  android: true
        dingtalk: "4.6.20.1)"
        ie: false
        ios: false
        os: "linux"
        weixin: false
    */

}