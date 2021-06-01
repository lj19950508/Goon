
<script>
    $(document).ready(function () {
        var drEvent = $('#${path!}').dropify(
            {
                error: {
                    'fileSize': 'The file size is too big ({{ value }} max).',
                    'minWidth': 'The image width is too small ({{ value }}}px min).',
                    'maxWidth': 'The image width is too big ({{ value }}}px max).',
                    'minHeight': 'The image height is too small ({{ value }}}px min).',
                    'maxHeight': 'The image height is too big ({{ value }}px max).',
                    'imageFormat': 'The image format is not allowed ({{ value }} only).',
                    'fileExtension': '文件格式不正确'
                },
                messages: {
                    'default': '添加图片',
                    'replace': '',
                    'remove':  'X',
                    'error':   '上传失败',
                }
            }
        );




        drEvent.on('dropify.fileReady', function(event, element){
            var formData = new FormData();
            var img_file = document.getElementById("${path!}");
            var fileObj = img_file.files[0];
            formData.append("file", fileObj);
            jp.block('#${path!}',"文件上传中...");
            $.ajax({
                url:'${ctx}/sys/file/webupload/upload?uploadPath=${uploadPath!}',
                type: 'POST',
                data: formData,
                async:false,
                processData:false,
                contentType:false,
                success: function (data) {
                    if(data.success){
                        $("input[name='${path}']").val(data.body.url);
                        jp.unblock('#${path!}');
                        //jp.info("上传成功!");
                    }
                }
            })



        });
        drEvent.on('dropify.beforeClear', function(event, element){
            //return confirm("确实要删除图片吗?");
        });
        drEvent.on('dropify.afterClear', function(event, element){
            /*jp.get('${ctx}/sys/file/webupload/deleteByUrl?url='+$("input[name='${path}']").val(),function (data) {
                if(data.success){
                    $("input[name='${path}']").val("");
                    jp.info("删除成功!");
                }else {
                    jp.info("删除失败!");
                }
            });*/
            $("input[name='${path}']").val("");
        });

    })

</script>
    <input type="hidden" name="${path!}" data-id="" value="${value!}" />

    <input type="file"  id="${path!}"
           data-show-remove="true"
           accept=".png,.jpg,.jpeg,.bmp,.gif,image/png,image/jpg,image/jpeg,image/bmp,image/gif"
           data-max-file-size-preview="100M"
           data-allowed-file-extensions="png jpg jpeg bmp gif"
           class="dropify" data-default-file="${value!}" title="" />