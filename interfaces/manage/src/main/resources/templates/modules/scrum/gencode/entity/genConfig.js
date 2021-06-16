<script>

$(function () {
    $form.submit("#genForm",function(data){
        if (data.success) {
            go_into("${ctx}/scrum/gencode/entity");
        }
    },null,{error:true})
})

</script>