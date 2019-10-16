function showLoading(){
    index = layer.load(2, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
}

function hideLoading(){
    layer.c(index)
}


function alertMsg(msg,staytime){
    if(!staytime){
        staytime = 1000;
    }
    layer.msg(msg,{time:staytime});
}

// 验证身份证号码
function checkIdNum(card) {
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    return pattern.test(card);
}

// 验证手机号
function isPhoneNo(phone) {
    var pattern = /^1[34578]\d{9}$/;
    return pattern.test(phone);
}

