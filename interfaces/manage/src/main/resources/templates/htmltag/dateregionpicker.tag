<input readonly class="form-control laydate-input"  id="${id}Datepicker"  />
<input type="hidden" name="start${strutil.upperFirst(name)}" id="start${strutil.upperFirst(name)}" />
<input type="hidden" name="end${strutil.upperFirst(name)}" id="end${strutil.upperFirst(name)}" />
<script type="text/javascript">
    let ${id}val = '${value!}';
    laydate.render({
        elem: '#${id}Datepicker'
        ,calendar: true
        ,value: ${id}val
        ,range: '~'
        ,trigger: 'click'
        ,theme:'#0069D9'
        ,done: function(value, date){
            //对value格式化
            let time = value.split('~');
            $("#start${strutil.upperFirst(id)}").val(time[0].trim())
            $("#end${strutil.upperFirst(id)}").val(time[1].trim())
        }
    });
</script>