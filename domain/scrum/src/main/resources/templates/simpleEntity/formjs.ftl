<script>
    var pageFormObject={
        startAllLisener:function(){
            $form.submit("#${entityName}Form",function(data){
                    if (data.success) {
                        go_into("${r'${ctx}'}/${moduleName}/${entityName}");
                    }
                },null,
                {error:true})
            ${r'<@ if(mode=="view"){ @>'}
            $('#${entityName}Form').find('input,textarea,select').prop('disabled',true);
            $('#submit').prop('disabled',true);
            ${r'<@ } @>'}
        },
        init:function () {
            this.startAllLisener();
        },

    };
    $(function () {
        pageFormObject.init()
    })

</script>