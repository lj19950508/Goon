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
        editor.customConfig.uploadImgServer ="/goon/manage/file/upload"
        editor.customConfig.uploadImgParams = {
            uploadPath: "domain/module"
        }
        editor.customConfig.uploadFileName = 'uploadFile'
        editor.customConfig.uploadImgMaxSize = 20 * 1024 * 1024;//设置图片大小为20M
        editor.customConfig.uploadImgTimeout = 1000000; //图片上传超时限制10s

        editor.customConfig.uploadImgHooks = {
            before: function(xhr) {
            },
            success: function(xhr) {
            },
            fail: function(xhr, editor, resData) {
            },
            error: function(xhr, editor, resData) {
            },
            timeout: function(xhr) {
            },
            customInsert: function(insertImgFn, result) {
                insertImgFn(result.data)
            }
        }

        editor.create()
        $("#${id}").val(editor.txt.html())
    })
</script>