<script>
var pageFormObject={
    startAllLisener:function(){

        $form.submit("#entityForm",function(data){
            if (data.success) {
                go_into("${ctx}/gencode/entity");
            }
        },null,{error:true})
        <@ if(mode=='view'){ @>
        $('#gencodeForm').find('input,textarea,select').prop('disabled',true);
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