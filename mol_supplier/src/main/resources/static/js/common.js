var loadingpointaddinter ;

/**
 * 显示加载图标
 * @param msg
 */
function showLoading(msg){
    if(msg){
        $("#loading_img_span").text(msg);
        loadingpointaddinter = setInterval(function(){
            $("#loading_img_span").text($("#loading_img_span").text()+'.');
            if($("#loading_img_span").text().indexOf('.....') >0){
                $("#loading_img_span").text(msg);
            }
        },1000)
    }
    var abc = $("#loading_div").attr('hidden');
    if(abc){
        $("#loading_div").removeAttr('hidden');
    }
}

/**
 * 隐藏加载图标
 */
function hideLoading(){
    if(loadingpointaddinter){
        clearInterval(loadingpointaddinter);
    }
    $("#loading_div").attr('hidden','hidden');
}

/**
 * 弹窗提示
 * @param msg       提示内容
 * @param staytime  窗口维持的毫秒数
 */
function alertMsg(msg,staytime){
    if(!staytime){
        staytime = 1000;
    }
    layer.msg(msg,{time:staytime});
}

/**
 * 验证身份证号码
 * @param card
 * @returns {boolean}
 */
function checkIdNum(card) {
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    return pattern.test(card);
}


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
 * 上传图片
 * @param bl                    图片的blob
 * @param whichImg              自定义的图片种类，例如身份证正面，反面，营业执照等，值是在后端的MicroAuthController类中定义的常量
 * @returns {Promise<any>}
 */
function uploadImg(bl,whichImg){
    //showLoading();
    var fd = new FormData();
    fd.append("file",bl);
    fd.append("whichImg",whichImg);
    return new Promise(function(resolve, reject){
        $.ajax({
            url: '/auth/upload/img',
            data:fd,
            type: 'post',
            //contentType: 'application/json;charset=UTF-8',
            // headers: {
            //     'Content-Type': 'application/x-www-form-urlencoded'  //multipart/form-data;boundary=--xxxxxxx   application/json
            // },
            contentType : false,
            processData: false,
            contentType: false,
            success:function(data){
                //hideLoading();
                alertMsg("上传成功！");
                resolve(data);
                console.log(data);
            },fail:function(data){
                //hideLoading();
                alertMsg("上传失败，请稍后再试!");
                reject(data);
            }
        })
    })
}


/**
 * 导航图标点击事件
 */
$.each($(".bottom_icon_img"),function(){
    var that = $(this);
    $(this).on('click',function(){
        var id=that.attr('id');
        var oldsrc = that.attr('src');
        var newfront = oldsrc.substring(0,oldsrc.indexOf('.'));
        var newsrc = newfront+'-flow.png';
        console.log(newsrc);
        $("#botton_icon_index_img_id").attr('src','/img/bottom-icon-index.png');
        $("#botton_icon_schedule_img_id").attr('src','/img/bottom-icon-schedule.png');
        $("#botton_icon_person_img_id").attr('src','/img/bottom-icon-person.png');
        $("#"+id).attr('src',newsrc);
        setTimeout(function(){
            window.location.href=that.next().attr('href');
        },600);
    })
})

function snyTimeOut(time,callback){
    return new Promise(function(resolve, reject){
        setTimeout(function(){
            callback;
        },time);
    })
}

/**
 * 去后端获取支付信息
 * @param payfor                1：申请成为战略供应商      2：申请成为单一供应商
 */
function getPayInfo(paramData){
    return  new Promise((resolve, reject) => {
        $.ajax({
            url:"/pay/alipay/getCreateInfo",
            data:paramData,
            dataType:"json",
            success:function(res){
                console.log(res);
                resolve(res);
            },
            fail:function(res){
                alertMsg("获取数据异常，请重试",3000);
                console.log(res);
                reject(res);
            }
        })
    })
    return
}

/**
 * 调起支付宝支付页面
 * @param payinfo
 * @param orderid
 */
function toalipay(payinfo,orderid){
    console.log("调用钉钉支付接口:，orderid:"+orderid);
    return new Promise((resolve, reject) => {
        dd.biz.alipay.pay({
            info:payinfo,
            onSuccess: function (result) {
                // {
                //     memo: 'xxxx', // 保留参数，一般无内容
                //         result: 'xxxx', // 本次操作返回的结果数据
                //     resultStatus: '' // 本次操作的状态返回值，标识本次调用的结果
                // }

                console.log("支付结果：");
                console.log(result);
                showLoading("查询支付结果中");
                resolve(orderid);
            },
            onFail: function (err) {
                reject(err);
            }
        });
    })
}

/**
 * 每2秒访问一次，查询状态
 * @param orderid
 * @param count
 */
function getOrderStatus(orderid,count,nextPageName){
    console.log("count is : ", count);

    if (count == 0) {
        console.log("All is Done!");
        hideLoading();
        alertMsg("支付未成功！如确认已支付请刷新或联系管理员");
        location.reload();
    }else{
        count -= 1;
        setTimeout(function() {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url:"/pay/alipay/getOrderStatus",
                    data:{'orderid':orderid},
                    dataType:"json",
                    success:function(res){
                        showLoading("查询支付结果中");
                        if(!res.success){
                            getOrderStatus(orderid,count,nextPageName);
                        }else{
                            alertMsg("支付成功");
                            setTimeout(function(){
                                if(nextPageName == '123'){
                                    window.history.back();
                                }else{
                                    location.href="/pay/alipay/showSuccess?turnPageName="+nextPageName;
                                }
                            },1500);
                        }
                    },
                    fail:function(res){
                        console.log(res);
                        reject(res);
                    }
                })
            })
        }, 1000);
    }
}





