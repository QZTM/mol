/**
 * 验证手机号码格式               checkPhone(phone)                   返回值为true或者false
 * 验证手机号码格式               isPhoneNo(phone)                    返回值为true或者false
 * 发送验证码                    sendMes(phone,msgType)              msgType:短信类型，1为注册，2为登陆验证
 * 验证验证码                    checkPhoneCode(phone,code)          promise
 * 获取验证码倒计时
 */


/**
 * 验证手机号码格式
 * @param phone
 * @returns {boolean}
 */
function checkPhone(phone){
    if(!(/^1[3456789]\d{9}$/.test(phone))){
        dd.device.notification.toast({
            icon: '', //icon样式，不同客户端参数不同，请参考参数说明
            text: "手机号码有误，请重填", //提示信息
            duration: 5, //显示持续时间，单位秒，默认按系统规范[android只有两种(<=2s >2s)]
            delay: 0, //延迟显示，单位秒，默认0
            onSuccess : function(result) {},
            onFail : function(err) {}
        })
        return false;
    }

};


/**
 * 验证手机号
 * @param phone
 * @returns {boolean}
 */
function isPhoneNo(phone) {
    var pattern = /^1[34578]\d{9}$/;
    return pattern.test(phone);
}

/**
 * 验证手机号与验证码
 * @param phone
 * @param code
 * @returns {Promise<unknown>}
 */
function checkPhoneCode(phone,code){
    return new Promise(function(resolve, reject){
        var jsonObj = {"phone":phone,"code":code};
        $.ajax({
            url: '/msg/check',
            data: JSON.stringify(jsonObj),
            type: 'post',
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            beforeSend: function () {

            },
            success: function (data) {
                console.log(data);
                resolve(data);
            },
            error: function (data) {
                console.log(data);
                reject(data);
            },
            complete: function () {

            }
        })
    })
}


/**
 * 发送短信
 * @param phone                         手机号码
 * @param msgType                       短信类型    1为注册，2为登陆验证
 * @returns {Promise<unknown>}
 */
function sendMes(phone,msgType) {
    console.log("sendMes....phone:" + phone + ",msgType...:" + msgType);
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: '/msg/send',
            data: {phone:phone,msgType:msgType},
            type: 'post',
            dataType: "json",
            timeout:10000,
            contentType: 'application/x-www-form-urlencoded',
            error: function(XMLHttpRequest, textStatus, errorThrown){
                alertMsg("发送失败，请稍后再试");
            },
            success: function (data) {
                console.log(data);
                resolve(data);
            },
            fail : function(res){
                console.log(res);
                reject(res);
            }
        })
    })
};