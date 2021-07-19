<input id="${id}" name="${name}" type="hidden" name="">
<div class="file-loading">
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
        // deleteUrl:function(obj){
        //     console.log(obj)
        // },
        deleteUrl: "/goon/manage/file/delete",
        //file参数
        uploadExtraData: {
            'uploadPath': '${uploadPath}', // for access control / security
        },
        // allowedFileTypes: ['image'],    // allow only images
        //初始化图片
        // initialPreview: [],          // if you have previously uploaded preview files
        // initialPreviewConfig: [],    // if you have previously uploaded preview files

    }).on('fileuploaded', function(event, data, index, fileId) {
        let val = $("#${id}").val()
        let valArr = [] ;
        if(val){
            valArr = val.split(',')
        }
        console.log()
        valArr.push(data.response.initialPreviewConfig[0].key)
        val = valArr.join(',');
        $("#${id}").val(val)
    }).on('filedeleted',function(event, key, jqXHR, data) {
        let val = $("#${id}").val()
        let valArr = val.split(',')
        valArr = valArr.filter(item=>{
            return item !=key;
        })
        val = valArr.join(',');
        $("#${id}").val(val)
    })
    //删除完成应该扣掉
})
</script>