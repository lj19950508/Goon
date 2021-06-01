<script>
var pageFormObject={
    startAllLisener:function(){

        $form.submit("#dictForm",function(data){
            if (data.success) {
                go_into("${ctx}/sys/dict");
            }
        },null,{error:true})
        <@ if(mode=='view'){ @>
        $('#dictForm').find('input,textarea,select').prop('disabled',true);
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