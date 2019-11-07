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
    alertMsg("获取用户信息中...请稍后",{time:2000});
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
            alertMsg("获取成功",{time:1500});
            if (!("初始化完成" == b.message )) {
                xunjia(4,0);
            }else{
                xunjia(4,1);
                //鉴权
                console.log("鉴权");
                console.log("b.result");
                console.log(b.result);
                console.log("agentId:"+b.result.agentId);
                console.log("corpId:"+b.result.corpId);
                console.log("timeStamp:"+b.result.timeStamp);
                console.log("nonceStr:"+b.result.nonceStr);
                console.log("signature:"+b.result.signature);
                dd.config({
                    'agentId': b.result.agentId,
                    'corpId': b.result.corpId,
                    'timeStamp': b.result.timeStamp,
                    'nonceStr': b.result.nonceStr,
                    'signature': b.result.signature,
                    'jsApiList': [
                        'runtime.info',
                        'device.notification.prompt',
                        'biz.chat.pickConversation',
                        'device.notification.confirm',
                        'device.notification.alert',
                        'device.notification.prompt',
                        'biz.chat.open',
                        'biz.util.open',
                        'biz.user.get',
                        'biz.contact.choose',
                        'biz.telephone.call',
                        'biz.util.uploadImage',
                        'biz.ding.post',
                        'biz.alipay.pay',
                        'biz.telephone.showCallMenu']
                });
            }
        }
    }).then(function(){
        console.log("执行成功");



        // return new Promise()
        //去后端获取订单信息

        // dd.ready(function() {
        //     dd.biz.alipay.pay({
        //         info:'alipay_sdk=alipay-sdk-java-3.7.110.ALL&app_id=2019072665956811&biz_content=%7B%22body%22%3A%22%E4%B8%93%E5%AE%B6%E8%AE%A4%E8%AF%81%E8%B4%B9%E7%94%A8%22%2C%22out_trade_no%22%3A%22orderId%22%2C%22product_code%22%3A%2212586%22%2C%22subject%22%3A%22%E8%AE%A4%E8%AF%81%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22200%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ffyycg66.vaiwan.com%2FCheck_Alipay&sign=cMQNJkq7JavSVF%2BXmQx%2BPeCXK3jkhCkq%2FYSD3MA3jysBCk4O4nhB9jmej8w%2B7YyBk59%2FIB3AR%2BgAbS1i%2FDwcMbivezTnw8bOloPG68xo4mEC0xIaVnYmdxEvZLx%2BohMKnKRtQLSsipwhvgiNvf19W6vnLRcqhcgIyHwOBot5HHGLyPo29ixr9sP%2B2SU5BbvC3Q8jZNlHGsDmqyBue8df9KAHLKhICMhLZ%2BZfOIaYDvUJ%2BeiptnjB4TuBZHigqZUkRDMaSY71yFhkHZkfuIptQGjOqq9%2FyQIV2EKkEbHYGQCVN3wov27yiOIWYdIRhheQMKj1EfKCz43mD3VTcLCXSw%3D%3D&sign_type=RSA2&timestamp=2019-10-15+16%3A24%3A27&version=1.0\n',
        //         onSuccess: function (result) {
        //             // {
        //             //     memo: 'xxxx', // 保留参数，一般无内容
        //             //         result: 'xxxx', // 本次操作返回的结果数据
        //             //     resultStatus: '' // 本次操作的状态返回值，标识本次调用的结果
        //             // }
        //             console.log(resule);
        //         },
        //         onFail: function (err) {
        //
        //         }
        //     });
        // });

    }).catch(function (reason) {
        console.log(reason);
        hideLoading();
    });
};










