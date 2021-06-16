<div class="custom-control custom-switch">
    <input value="${value!'false'}"  type="hidden" id="${id}" name="${name}" >
    <@ if(value!'false' == 'true'){ @>
    <input ${required!true==true?'':'required="required"'} checked id="${id}Switch"  type="checkbox" class="custom-control-input" onchange="${id}Change(this)" >
    <@ }else{ @>
    <input  id="${id}Switch"  type="checkbox" class="custom-control-input" onchange="${id}Change(this)" >
    <@ } @>
    <label class="custom-control-label" for="${id}Switch"></label>
</div>

<script>

    function ${id}Change(self){
        if($(self).is(":checked")){
            $("#${id}").val(true)
        }else{
            $("#${id}").val(false)
        }
    }

</script>