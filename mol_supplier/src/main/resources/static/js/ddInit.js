/*!
* jQuery Migrate - v1.0.0 - 2013-01-14
* https://github.com/jquery/jquery-migrate
* Copyright 2005, 2013 jQuery Foundation, Inc. and other contributors; Licensed MIT
*/
/**
 * 钉钉初始化相关
 * @type {string}
 */
var access_code = '';
var run_env = '';

/*获取运行环境*/
function getRunEnv(){
    var dev = "",env = "";
    var dd="dd",wx="wx",wxwork="wxwork",pc="pc",android="android",iphone="iphone",other="other";
    //判断打开页面的环境
    var ua = navigator.userAgent.toLowerCase();
    //判断是手机还是电脑
    if (/(iPhone|iPad|iPod|iOS|Android)/i.test(navigator.userAgent)) {
        //移动端
        //苹果和Ipad设备
        if(navigator.userAgent.indexOf('iPhone') > -1 || /iPad/gi.test(navigator.userAgent)){
            dev=iphone;
        }else{
            dev=android;
        }
        if(ua.indexOf('dingtalk') !=-1){
            env = dd;
        }else if(ua.match(/MicroMessenger/i)=="micromessenger") {
            if(ua.indexOf("wxwork")!=-1){
                alert("在企业微信中打开的")
                env = wxwork;
            }else{
                env=wx;
            }
        }else{
            env=other;
        }
    }else{
        dev=pc;
    }
    run_env = dev+"----"+env;
    console.log("runEnv:"+run_env);
    return run_env;
};
// <!--安卓手机，钉钉微应用首页默认执行：
//     1.获取免登授权码
//     2.根据免登授权码和access_token(后端根据AppKey和AppSecret获取的)获取用户信息、部门信息、组织信息
//     3.根据获取到的相关信息去数据库查询该企业、该业务员的注册状态（如果未注册会提示去注册，如果已经注册了那么会获取到s-ticket操作票据）
//     4.如果点击去注册，会把数据缓存，然后跳转到注册页面进行注册业务
//     -->
/*获取免登授权码*/
function get_access_code(){
    console.log("get_access_code()");
    return dd.runtime.permission.requestAuthCode({
        corpId: "ding6ef23b66fc0611a335c2f4657eb6378f",
        onSuccess: function(result) {
            access_code = result.code;
            console.log("access_code:"+result.code);
        },
        onFail : function(err) {
            dd.device.notification.toast({
                icon: '', //icon样式，不同客户端参数不同，请参考参数说明
                text: "获取免登授权码失败，请重启应用后重试", //提示信息
                duration: 5, //显示持续时间，单位秒，默认按系统规范[android只有两种(<=2s >2s)]
                delay: 0, //延迟显示，单位秒，默认0
                onSuccess : function(result) {
                    access_code = "";
                },
                onFail : function(err) {

                }
            })
        }
    })
};

/*获取userInfo*/
function getUserAndOrgByEnvWithdd(){
    console.log("getUserAndOrgByEnvWithdd()");
    return new Promise(function(resolve,reject){
        $.ajax({
            url: '/microApp/login/initUserInfo',
            data: {
                code:access_code
            },
            type: 'get',
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            cache: false,
            beforeSend: function(){

            },
            success: function (data) {
                console.log("getUserAndOrgByEnvWithdd()...success...");
                resolve(data);
            },
            error: function (xhr) {
                reject(xhr);
            },
            complete: function () {

            }
        })
    });
};


/*获取订单信息*/
function getOrderInfo(){
    console.log("getOrderInfo()");
    alertMsg("获取订单信息中...",{time:1000});
    return new Promise(function(resolve,reject){
        $.ajax({
            url: '/microApp/alipay/getOrderInfo',
            data: {
                code:access_code
            },
            type: 'get',
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            cache: false,
            beforeSend: function(){

            },
            success: function (data) {
                console.log("getUserAndOrgByEnvWithdd()...success...");
                resolve(data);
            },
            error: function (xhr) {
                reject(xhr);
            },
            complete: function () {

            }
        })
    });
};

/*安卓钉钉初始化*/
function android_dd_init_action(){
    console.log("android_dd_init_action")
    //alertMsg("获取用户信息中...请稍后",2000);
    showLoading("获取用户信息中");
    Promise.resolve().then(function(){
        return  get_access_code();
    }).then(function(a){
        //showLoading();
    }).then(function(){
        return getUserAndOrgByEnvWithdd();
    }).then(function(b){
        if (!b.success) {
            //hideLoading();
            window.location.href = "/microApp/login/show";
        }else{
            localStorage.setItem("user",JSON.stringify(b.result.user));
            localStorage.setItem("supplier",JSON.stringify(b.result.supplier));
            alertMsg("获取成功",1500);
            if (!("初始化完成" == b.message )) {
                xunjia(4,0);
            }else{
                xunjia(4,1);
                hideLoading();
            }
        }
    }).then(function(){
        console.log("执行成功");
        hideLoading();
    }).catch(function (reason) {
        console.log(reason);
        hideLoading();
    });
};










