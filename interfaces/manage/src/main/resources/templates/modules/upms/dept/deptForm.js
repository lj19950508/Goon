<script>
var pageFormObject={
    startAllLisener:function(){

        $form.submit("#deptForm",function(data){
            if (data.success) {
                go_into("${ctx}/upms/dept");
            }
        },null,{error:true})

        <@ if(mode=='view'){ @>
            $('#deptForm').find('input,textarea,select').prop('disabled',true);
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