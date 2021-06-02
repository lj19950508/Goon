<%--日期选择器组件--%>
<input readonly ${required!true==true?'':'required="required"'}" class="form-control laydate-input"  id="${path}" name="${path}" />
<script type="text/javascript">
    //对value进行格式化
    let ${path}val = '${value!}';
    laydate.render({
        elem: '#${path}'
        ,calendar: true
        ,trigger: 'click'
        ,value: ${path}val
        ,theme:'#0069D9'
    });
    //value格式化
</script>