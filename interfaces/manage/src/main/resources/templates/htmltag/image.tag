<input type="hidden" id="${id!}" name="${name!}" value="${value!}" >
<div id="${name}uploader" class="uploader webuploader imguploader">
    <div id="${name}_file_list" class="uploader-list">
    </div>
    <@ if(readOnly=='false'){ @>
    <div id="${name}btns" class="btns ${hideBtn!'false'=='true' ? 'hide' : ''}">
        <div id="${name}picker" title="点击选择文件上传" ><i class="fa fa-plus"></i> 添加图片</div>
    </div>
    <@ }@>
</div>
<script type="text/javascript">
    $(function(){
        ${name}Init();
    })

    var ${name}fileNum = 0;
    var ${name}uploader;
    //初始化上传控件
    function ${name}Init() {
        var fileNumLimit = '${fileNumLimit!'9'}';
        var fileSingleSizeLimit = '${fileSingleSizeLimit!'5'}';
        var type = '${type!}';
        var title = "";
        var allowedExtensions = 'gif,jpg,jpeg,bmp,png';
        var mimeTypes = 'image/*';
        var accept = {};
        var infoMsg = "";
        if (fileNumLimit) {
            infoMsg += "最多可上传"+fileNumLimit+"张图片，建议使用1068x455像素的图片，";
        }
        if (fileSingleSizeLimit) {
            infoMsg += "图片大小不超过"+fileSingleSizeLimit+"M，";
        }
        if ("*" != allowedExtensions) {
            infoMsg += "支持"+allowedExtensions+" 类型图片文件";
        }
        if (infoMsg.charAt(infoMsg.length - 1) == "，") {
            infoMsg = infoMsg.substring(0, infoMsg.length - 1);
        }
        $("#${name}uploader").find(".btns").append("<div class='webuploader-tip'>"+infoMsg+"</div>");


        ${name}uploader = WebUploader.create({
            auto: true,
            dnd: '#${name}_file_list',
            server: '${ctx}/file/upload?uploadPath=${uploadPath!}',
            pick: {
                id:'#${name}picker',
                multiple:true
            },
            fileSingleSizeLimit: 1024 * 1024 * fileSingleSizeLimit,
            compress:false,
            fileNumLimit: fileNumLimit,
            accept: {
                title: title,
                extensions: allowedExtensions,
                mimeTypes: mimeTypes
            },
            duplicate: true
        });
        ${name}fileNum = ${name}init($("#${name}_file_list"), $("input[name='${name!}']").val());
        ${name}showOrHideAddBtn(${name}fileNum)
        // 当有文件被添加进队列的时候
        ${name}uploader.on('beforeFileQueued', function (file) {
            if (file.size == 0) {
                jp.info("请勿上传内容为空的文件");
                return false;
            }
            if (fileNumLimit == '' || fileNumLimit == 0) {
                return true;
            }
            if (${name}fileNum >= fileNumLimit) {
                this['trigger']("error", 'Q_EXCEED_NUM_LIMIT', fileNumLimit, file);
            }
            return ${name}fileNum >= fileNumLimit ? false : true;
        });
        ${name}uploader.on('fileQueued', function (file) {
            if (${name}uploader.fileSingleSizeLimit> 0 && file.size > ${name}uploader.fileSingleSizeLimit) {
                jp.warning("文件大小不能超过"+${name}uploader.fileSingleSizeLimit+"M");
                return;
            }
            var $uploadHtml = '<div id="' + file.id + '" class="item uploading">\n' +
                '            <a class="btn-cancel" href="javascript:void(0);" onclick="${name}delFile(this)" title="取消上传"><i class="fa fa-times"></i></a>\n' +
                '            <div class="pro-info">\n' +
                '                <span class="state">文件上传中</span>\n' +
                '                <div class="progress">\n' +
                '                    <div class="progress-bar" style="width: 0%;">\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>';
            $("#${name}_file_list").append($uploadHtml);
        });
        ${name}uploader.on('fileDequeued', function (file) {
        });
        ${name}uploader.on("error", function (type) {
            if (type == "F_DUPLICATE") {
                jp.info("请不要重复选择文件！");
            } else if (type == "Q_EXCEED_NUM_LIMIT") {
                jp.info("最多只能上传" + fileNumLimit + '个文件');
            } else if (type == "Q_TYPE_DENIED") {
                jp.info("图片格式不正确");
            } else if ("Q_EXCEED_SIZE_LIMIT" == type || "F_EXCEED_SIZE" == type) {
                jp.info("图片不能超过"+fileSingleSizeLimit+"M");
            } else {
                jp.info("上传出错！请检查后重新上传！错误代码" + type);
            }
        });
        // 文件上传过程中创建进度条实时显示。
        ${name}uploader.on('uploadProgress', function (file, percentage) {
            $("#${name}_file_list").find('#' + file.id).find('.progress-bar').css("width", percentage * 100 + '%')
            if(percentage >=1 ){
                $("#${name}_file_list").find('#' + file.id).removeClass("uploading");
            }
        });
        ${name}uploader.on('uploadSuccess', function (file, response) {
            $('#' + file.id).find('.state').remove();
            var size = '0';
            var fileSize = file['size'];
            if (fileSize == 0) {
                size = '0';
            } else if (fileSize < 1024) {
                size = fileSize + 'b';
            } else if (fileSize < 1024 * 1024) {
                size = Math["floor"](fileSize / 1024 * 100) / 100 + 'KB';
            } else {
                size = Math["floor"](fileSize / 1024 / 1024 * 100) / 100 + 'M';
            }
            var data = {'uid': file['id'], 'name': decodeURIComponent(response['data']['substring'](response['data']['lastIndexOf']('/') + 1)), 'url': response['data'], 'size': size};
            $('#' + file.id).html(${name}addFile(data));
            var value = $("#${id!}").val();
            if (value) {
                value += "|" + data.url;
            } else {
                value = data.url;
            }
            $("#${id!}").val(value);
            ${name}showOrHideAddBtn(++${name}fileNum)
        });

        ${name}uploader.on('uploadError', function (file) {
            $('#' + file.id).find('.state').text('上传出错');
        });

        ${name}uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.info .pro-bar').fadeOut();
        });

    }


    function ${name}init($list, fileValues) {
        var sum = 0;
        if (fileValues) {
            $.ajax({
                url: "${ctx}/sys/file/webupload/data?fileValues=" + encodeURIComponent(fileValues),
                method: "get",
                async: false,
                success: function (data) {
                    var fileIds = data['body']['fileIds'].split("|");
                    var urls = fileValues.split("|");
                    var sizes = data['body']['fileSizes'].split("|");
                    for (var i = 0; i < urls.length; i++) {
                        if (urls[i] != "") {
                            sum++;
                            var data = {
                                uid: "-1",
                                name: decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/") + 1)),
                                id: fileIds[i],
                                url: urls[i],
                                size: sizes[i]
                            }
                            $("#${name}_file_list").append('<div id="' + data.id + '" class="item">' +
                                ${name}addFile(data) +
                                '</div>');
                        }
                    }
                }
            });
        }
        return sum;
    }

    function ${name}addFile(data) {
        var row = ' <img src="'+data.url+'">\n' +
            '            <div class="mask">\n' +
            '                <div class="btn-wrap">\n' +
            '                    <a class="btn-view" href="javascript:void(0);" onclick="showPic(\''+data.url+'\')" title="预览文件"><i class="fa fa-eye"></i></a>\n' +
            <@ if(readOnly=='false'){ @>
            '                    <a class="btn-del" href="javascript:void(0);" onclick="${name}delFile(this)" title="删除文件"><i class="fa fa-trash-o"></i></a>\n' +
            <@ } @>
            '                </div>\n' +
            '            </div>';
        return row;
    }

    //删除附件
    function ${name}delFile($this) {
        var url = $($this).parents(".item").find("img").attr("src");
        jp.get("${ctx}/sys/file/webupload/deleteByUrl?url=" + encodeURIComponent(url), function (data) {
            var value = $($this).parents("#${name}uploader").prev("input").val();
            if (value && value.indexOf(url) != -1) {
                value = value + "|";
                value = value.replace(url + "|", "");
            }
            if (value.charAt(value.length - 1) == "|") {
                value = value.substring(0, value.length - 1);
            }
            $($this).parents("#${name}uploader").prev("input").val(value);
            $($this).parents(".item").remove();
            ${name}showOrHideAddBtn(--${name}fileNum);
            var files = ${name}uploader.getFiles("complete");
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                if (url.search(file.name.substring(0, file.name.indexOf(".")))>0) {
                    ${name}uploader.removeFile(file, true);
                }
            }
        })

    }

    //显示或隐藏添加按钮
    function ${name}showOrHideAddBtn(fileNum) {
        if(fileNum>=parseInt('${fileNumLimit!9}')){
            $("#${name}btns").hide()
        }else {
            $("#${name}btns").show()
        }
    }
</script>

