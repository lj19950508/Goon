<script>
$(function(){
    //如果当前页面不是最顶级页面，当然页面重新定向 ， 防止iframe内嵌登录页面。
    if (window != top) {top.location.href = location.href;}
})


</script>