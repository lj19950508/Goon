<input type="hidden" id="${id!}" name="${name!}" value="${value!}">
<div id="${name}uploader" class="uploader webuploader">
    <div class="btns ${hideBtn ? 'hidden' : ''}">
        <div id="${name}picker"><i class="fa fa-cloud-upload"></i> 添加文件</div>
    </div>
    <div id="${name}_file_list" class="uploader-list">
    </div>
</div>
<script type="text/javascript">

    $(function(){
        ${name}Init();
    })


    var ${name}upload;
    //初始化上传控件
    function ${name}Init() {
        var fileNumLimit = '${fileNumLimit!5}';
        var fileSingleSizeLimit = '${fileSingleSizeLimit!20}';
        var type = '${type!}';
        var title = "";
        var allowedExtensions = "${allowedExtensions!'*'}";
        var mimeTypes = "${mimeTypes!'*/*'}";
        var accept = {};
        if ("images" == type) {
            title='Images';
            allowedExtensions='gif,jpg,jpeg,bmp,png';
            mimeTypes='image/*';
        } else if ("video" == type) {
            title='video';
            allowedExtensions='3gp,mp4,rmvb,mov,avi,m4v,wmv';
            mimeTypes = 'video/*,audio/*';
        } else if ("w3cvideo" == type) {
            title='video';
            allowedExtensions='mp4,mov';
            mimeTypes='video/*,audio/*';
        } else if ("office" == type) {
            title='office';
            allowedExtensions='txt,xls,xlsx,doc,docx,ppt,pptx,pdf';
            mimeTypes='.txt,.xls,.xlsx,.doc,.docx,.ppt,.pptx,.pdf';
        } else {
            title='file';
            allowedExtensions= allowedExtensions;
            mimeTypes=mimeTypes;
        }
        var infoMsg = "";
        if (fileNumLimit) {
            infoMsg += "最多可上传"+fileNumLimit+"个文件，";
        }
        if (fileSingleSizeLimit) {
            infoMsg += "每个文件小于"+fileSingleSizeLimit+"MB，";
        }
        if ("*" != allowedExtensions) {
            infoMsg += "格式包括："+allowedExtensions+" 的文件";
        }
        if (infoMsg.charAt(infoMsg.length - 1) == "，") {
            infoMsg = infoMsg.substring(0, infoMsg.length - 1);
        }
        $("#${name}uploader").find(".btns").append("<span class='webuploader-tip'>"+infoMsg+"</span>");


        ${name}upload = WebUploader.create({
            auto: true,
            dnd: '#${name}_file_list',
            server: '${ctx}/file/upload?uploadPath=${uploadPath!}',
            pick: '#${name}picker',
            fileSingleSizeLimit: 1024 * 1024 * fileSingleSizeLimit,
            fileNumLimit: fileNumLimit,
            accept: {
                title: title,
                extensions: allowedExtensions,
                mimeTypes: mimeTypes
            },
            duplicate: true
        });
        var fileNum = ${name}init($("#${name}_file_list"), $("input[name='${name!}']").val());
        // 当有文件被添加进队列的时候
        ${name}upload.on('beforeFileQueued', function (file) {
            if (file.size == 0) {
                jp.info("请勿上传内容为空的文件");
                return false;
            }
            if (fileNumLimit == '' || fileNumLimit == 0) {
                return true;
            }
            if (fileNum >= fileNumLimit) {
                this['trigger']("error", 'Q_EXCEED_NUM_LIMIT', fileNumLimit, file);
            }
            return fileNum >= fileNumLimit ? false : true;
        });
        ${name}upload.on('fileQueued', function (file) {
            if (${name}upload.fileSingleSizeLimit> 0 && file.size > ${name}upload.fileSingleSizeLimit) {
                layer.warning("文件大小不能超过"+${name}upload.fileSingleSizeLimit+"M");
                return;
            }

            $("#${name}_file_list").prepend('<div id="' + file.id + '" class="item"><h4 class="info">' + file.name + ' <div class="pro-bar"></div><span class="state">等待上传...</span></h4></div>');
        });
        ${name}upload.on('fileDequeued', function (file) {
        });
        ${name}upload.on("error", function (type) {
            if (type == "F_DUPLICATE") {
                layer.info("请不要重复选择文件！");
            } else if (type == "Q_EXCEED_NUM_LIMIT") {
                layer.info("最多只能上传" + fileNumLimit + '个文件');
            } else if (type == "Q_TYPE_DENIED") {
                layer.info("您上传的文件类型不符，请检查");
            } else if ("Q_EXCEED_SIZE_LIMIT" == type || "F_EXCEED_SIZE" == type) {
                layer.info("你选择的文件大小超出"+fileSingleSizeLimit+"MB，请检查");
            } else {
                layer.info("上传出错！请检查后重新上传！错误代码" + type);
            }
        });
        // 文件上传过程中创建进度条实时显示。
        ${name}upload.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.info .pro-bar');
            $li.find('.state').text('上传中...');
            $percent.css('width', percentage * 100 + '%');
        });
        ${name}upload.on('uploadSuccess', function (file, response) {
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

            $('#' + file.id).find('.info').html(${name}addFile(data));
            var value = $("#${id!}").val();
            if (value) {
                value += "|" + data.url;
            } else {
                value = data.url;
            }
            $("#${id!}").val(value);
            fileNum++;
        });

        ${name}upload.on('uploadError', function (file) {
            $('#' + file.id).find('.state').text('上传出错');
        });

        ${name}upload.on('uploadComplete', function (file) {
            $('#' + file.id).find('.info .pro-bar').fadeOut();
        });
        $("#${name}uploader").on("click",".btnDel",function(){
            ${name}delFile(this);
            fileNum--;
        })
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
                            $("#${name}_file_list").append('<div id="' + data.id + '" class="item"><h4 class="info">' +
                                ${name}addFile(data) +
                                '</h4>' +
                                '</div>');
                        }
                    }
                }
            });
        }
        return sum;
    }

    function ${name}addFile(data) {
        var row = '<a class="filename" title="' + data.name + '" href="' + data.url + '">' + data.name + '</a><span class="filesize">（' + data.size + '）</span>';
        if (!${hideBtn!}) {
            row += "<a class=\"btnDel\"><i class=\"glyphicon glyphicon-remove\"></i>" + "</a>";
        }
        return row;
    }

    //删除附件
    function ${name}delFile($this) {
        var url = $($this).parent().find(".filename").attr("href");
        jp.get("${ctx}/sys/file/webupload/deleteByUrl?url=" + encodeURIComponent(url), function (data) {
            var value = $($this).parents(".uploader:first").prev("input").val();
            if (value && value.indexOf(url) != -1) {
                value = value + "|";
                value = value.replace(url + "|", "");
            }
            if (value.charAt(value.length - 1) == "|") {
                value = value.substring(0, value.length - 1);
            }
            $($this).parents(".uploader:first").prev("input").val(value);
            $($this).parent().parent().remove();
            var files = ${name}upload.getFiles("complete");
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                if (url.search(file.name.substring(0, file.name.indexOf(".")))>0) {
                    ${name}upload.removeFile(file, true);
                }
            }
        })
    }
</script>

