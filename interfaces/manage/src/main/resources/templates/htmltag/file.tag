<div class="file-loading">
    <input id="${id}" name="${name}" type="hidden" name="">
    <input id="${name}file"  name="uploadFile"  type="file" multiple  >
</div>

<script type="text/javascript">
$(function () {
    $("#${name}file").fileinput({
        language: 'zh',
        theme:'fas',
        browseOnZoneClick: true,
        minFileCount: 1,
        maxFileCount: 5,
        initialPreviewAsData: true,
        overwriteInitial: false,//不覆盖图片
        //上传地址
        uploadUrl: "/goon/manage/file/upload",
        uploadAsync:false,
        // deleteUrl:function(obj){
        //     console.log(obj)
        // },
        // deleteUrl: "/goon/manage/file/delete",
        //file参数
        uploadExtraData: {
            'uploadPath': '${uploadPath}', // for access control / security
        },
        // allowedFileTypes: ['image'],    // allow only images
        //初始化图片
        // initialPreview: [],          // if you have previously uploaded preview files
        // initialPreviewConfig: [],    // if you have previously uploaded preview files

    }).on('fileuploaded', function(event, previewId, index, fileId) {
        console.log('File Uploaded', 'ID: ' + fileId + ', Thumb ID: ' + previewId);
    }).on('fileuploaderror', function(event, data, msg) {
        console.log('File Upload Error', 'ID: ' + data.fileId + ', Thumb ID: ' + data.previewId);
    }).on('filebatchuploadcomplete', function(event, preview, config, tags, extraData) {
        console.log('File Batch Uploaded', preview, config, tags, extraData);
    })
})
</script>