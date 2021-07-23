<input id="${id}" name="${name}" value="${value!}" type="hidden" name="">
<div class="file-loading">
    <input id="${name}file"  name="uploadFile"  type="file" multiple  >
</div>

<script type="text/javascript">
$(function () {
    let val = '${value!}';
    let initalPreview =[];
    let initialPreviewConfig = [];
    if(val){
        $req.get("${ctx}/file/info",{urls:val},function(res){
            initalPreview = res.initialPreview
            initialPreviewConfig = res. initialPreviewConfig
            init${name}FileSelect(initalPreview,initialPreviewConfig)
        })
    }else{
        init${name}FileSelect(initalPreview,initialPreviewConfig)
    }

})

function init${name}FileSelect(initalPreview,initialPreviewConfig){
    $("#${name}file").fileinput({
        language: 'zh',
        theme:'fas',
        browseOnZoneClick: true,
        minFileCount: 1,
        maxFileCount: 5,
        initialPreviewAsData: true,
        overwriteInitial: false,//不覆盖图片
        //上传地址
        uploadUrl: "${ctx}/file/upload",
        //file参数
        uploadExtraData: {
            'uploadPath': '${uploadPath}', // for access control / security
        },
        // allowedFileTypes: ['image'],    // allow only images
        //初始化图片
        initialPreview:initalPreview,          // if you have previously uploaded preview files
        initialPreviewConfig:initialPreviewConfig,    // if you have previously uploaded preview files

    }).on('fileuploaded', function(event, data, index, fileId) {
        let val = $("#${id}").val()
        let valArr = [] ;
        if(val){
            valArr = val.split(',')
        }
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
}

</script>