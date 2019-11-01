function showLoading(){
    // var index = layer.load(2, {
    //     shade: [0.1,'#fff'] //0.1透明度的白色背景
    // });
}

function hideLoading(){
    if(index){
        layer.close(index);
    }
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


//导航图标点击事件
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

