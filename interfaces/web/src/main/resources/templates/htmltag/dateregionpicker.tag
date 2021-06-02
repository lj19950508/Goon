<%--日期期间组件--%>
<input readonly class="form-control laydate-input"  id="${path}Datepicker"  />
<input type="hidden" name="start${strutil.upperFirst(path)}" id="start${strutil.upperFirst(path)}" />
<input type="hidden" name="end${strutil.upperFirst(path)}" id="end${strutil.upperFirst(path)}" />
<script type="text/javascript">
    let ${path}val = '${value!}';
    laydate.render({
        elem: '#${path}Datepicker'
        ,calendar: true
        ,value: ${path}val
        ,range: '~'
        ,trigger: 'click'
        ,theme:'#0069D9'
        ,done: function(value, date){
            //对value格式化
            let time = value.split('~');
            $("#start${strutil.upperFirst(path)}").val(time[0].trim())
            $("#end${strutil.upperFirst(path)}").val(time[1].trim())
        }
    });
</script>