<script>
var pageFormObject={
    startAllLisener:function(){

        $form.submit("#adminForm",function(data){
            if (data.success) {
                go_into("${ctx}/upms/admin");
            }
        },function(){

        },{error:true})

        <@ if(mode=='view'){ @>
        $('#adminForm').find('input,textarea,select').prop('disabled',true);
        $('#submit').prop('disabled',true);
        <@ } @>

    },
    init:function () {
        this.startAllLisener();
    }

};


$(function () {
    pageFormObject.init()
})


</script>