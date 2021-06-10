layer.info=function(msg){
    return top.layer.msg(msg);
}
layer.warning=function(msg) {//通知
    return top.layer.msg(msg, {icon: 0});
}
layer.success=function(msg){
    return top.layer.msg(msg, {icon:1});
}
layer.error=function(msg){
    return top.layer.msg(msg, {icon:2});
}
