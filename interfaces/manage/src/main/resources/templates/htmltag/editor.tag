<textarea  style="display: none" id="${id}" name="${name}"></textarea>
<div id="${id}Editor"  >${value!''}</div>
<script type="text/javascript">

    $(function(){
        var wangEditor = window.wangEditor
        var editor = new wangEditor('#${id}Editor')
        editor.customConfig.onchange = function (html) {
            // 监控变化，同步更新到 textarea
            $("#${id}").val(html);
        }
        editor.create()
        $("#${id}").val(editor.txt.html())
    })
</script>