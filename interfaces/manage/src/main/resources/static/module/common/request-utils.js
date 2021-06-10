//todo 处理302 超时的任务
var $form = {
    submit: function (id, callback, beforeCB,layerOptions,extendData) {
        if(layerOptions){
            layerOptions = $.extend(true,reqLayerOption,layerOptions)
        }
        $(id).ajaxForm({
            dataType:'json',
            beforeSubmit:function(arr,form,options){
                if($(id)[0].checkValidity()===false){
                    $(id).addClass("was-validated");
                    return false;
                }
                layerOptions&&layerOptions.showLoading();
                beforeCB && beforeCB(arr,form,options)
            },
            error:function(xhr,textStatus){
                //网络错误
                layerOptions&&layerOptions.showNetError(textStatus)
                layerOptions&&layerOptions.closeLoading();
            },
            success:function(data,statusText,xhr,form){
                if(data.success){
                    layerOptions&&layerOptions.showSuccess(data.msg)
                }else{
                    layerOptions&&layerOptions.showError(data.msg)
                }
                layerOptions&&layerOptions.closeLoading();
                callback(data);
            }
        },extendData)
    }
}


var $req = {
    get:function(url,data,callback,layerOptions){
        if(layerOptions){
            layerOptions = $.extend(true,reqLayerOption,layerOptions)
        }
        layerOptions && layerOptions.showLoading();
        $.get(url,data,function(res,status){
            if(res.success){
                layerOptions && layerOptions.showSuccess(res.msg)
            }else{
                layerOptions && layerOptions.showError(res.msg)
            }
            layerOptions && layerOptions.closeLoading();
            typeof callback=="function" && callback(res)
        })
    },
    post:function (url,data,callback,layerOptions) {
        if(layerOptions){
            layerOptions = $.extend(true,reqLayerOption,layerOptions)
        }
        layerOptions && layerOptions.showLoading();
        $.post(url,data,function(res,status){
            if(res.success){
                layerOptions && layerOptions.showSuccess(res.msg)
            }else{
                layerOptions && layerOptions.showError(res.msg)
            }
            layerOptions && layerOptions.closeLoading();
            typeof callback=="function" && callback(res);
        })
    },
    jsonPost:function(url,data,callback,layerOptions){
        if(layerOptions){
            layerOptions = $.extend(true,reqLayerOption,layerOptions)
        }
        layerOptions && layerOptions.showLoading();
        $.ajax({
            type:'post',
            contentType:"application/json",
            traditional:true,
            url:url,
            data:JSON.stringify(data),
            success:function(res){
                if(res.success){
                    layerOptions && layerOptions.showSuccess(res.msg)
                }else{
                    layerOptions && layerOptions.showError(res.msg)
                }
                layerOptions && layerOptions.closeLoading();
                typeof callback=="function" && callback()
            },
            error:function() {
            }
        })
    },
    upload:function(url,file,callback,layerOptions){

        console.log(file.files[0])
        var formdata = new FormData();
        formdata.append('file',file.files[0]);
        if(layerOptions){
            layerOptions = $.extend(true,reqLayerOption,layerOptions)
        }
        layerOptions && layerOptions.showLoading();

        $.ajax({
            url: url,
            type: "POST",
            data: formdata,
            contentType: false,
            processData: false,
            success: function (res) {
                if(res.success){
                    layerOptions && layerOptions.showSuccess(res.msg)
                }else{
                    layerOptions && layerOptions.showError(res.msg)
                }
                layerOptions && layerOptions.closeLoading();
                typeof callback=="function" && callback(res);
            },
            error:function(data){
            }
        });
    }

}


var reqLayerOption = {
    //加载数据是否展示 默认都不弹窗 需要时 传入对象 {loading:true} 等 即可  关于这个index..
    index:null,
    loading:false,
    success:false,
    error:false,
    neterror:false,
    showLoading:function(){
        if(this.loading){
            this.index =  layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
        }
    },
    //数据加成成功是否显示
    showSuccess:function(msg){
        if(this.success) {
            layer.success(msg)
        }
    },
    //数据加载失败是否显示
    showError:function(err){
        if(!err){
            err="加载失败";
        }
        if(this.error) {
            layer.error(err);
        }
    },
    showNetError:function(err){
        if(!err){
            err="加载失败";
        }
        if(this.neterror) {
            layer.error(err)
        }
    },
    closeLoading:function(){
        if(this.loading) {
            layer.close(this.index);
        }
    }
}

