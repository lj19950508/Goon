<script>
var pageFormObject={
    startAllLisener:function(){

        $form.submit("#paramForm",function(data){
            if (data.success) {
                go_into("${ctx}/sys/param");
            }
        },null,
        //     function(arr,form,options){
        //     arr.push({name:"value",value:$("#value").html()});
        // },
            {error:true})
        <@ if(mode=='view'){ @>
        $('#paramForm').find('input,textarea,select').prop('disabled',true);
        $('#submit').prop('disabled',true);
        <@ } @>

    },
    init:function () {
        this.startAllLisener();
        this.switchValue()
    },

};

$(function () {
    pageFormObject.init()
})

</script>