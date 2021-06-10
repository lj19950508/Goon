<input readonly ${required!true==true?'':'required="required"'}" class="form-control laydate-input"  id="${id}" name="${name}" />
<script type="text/javascript">
    //对value进行格式化
    let ${id}val = '${value!}';
    laydate.render({
        elem: '#${id}'
        ,calendar: true
        ,trigger: 'click'
        ,value: ${id}val
        ,theme:'#0069D9'
    });
    //value格式化
</script>