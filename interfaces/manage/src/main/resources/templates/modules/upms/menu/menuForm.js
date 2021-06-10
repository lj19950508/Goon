<script>
var pageFormObject={
    startAllLisener:function(){

        $form.submit("#menuForm",function(data){
            if (data.success) {
                go_into("${ctx}/upms/menu");
            }
        },null,{error:true})

        <@ if(mode=='view'){ @>
            $('#menuForm').find('input,textarea,select').prop('disabled',true);
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