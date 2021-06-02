<%--未启用--%>
<input type="hidden" name="${name!}"  value="${value!}"/>
<span class="jp-input-search jp-input-search-enter-button jp-input-affix-wrapper">
		<input id="${id!}" readonly="readonly"
               class="${class!'jp-input'}" style="cursor: pointer;${cssStyle!}" onclick="${id!}FileDialogOpen();" placeholder="${placeholder!'请选择附件'}"/>
		<span class="jp-input-suffix">
			<button type="button"   id="${id!}Button" onclick="${id!}FileDialogOpen();"  class="jp-btn jp-input-search-button jp-btn-primary  ${disabled!} ${hideBtn!'false'=='true' ? 'hide' : ''}"><i class="fa fa-cloud-upload"></i></button>
		     <% if(allowClear!'false' == 'true'){ %>
		     	<button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute;top: 5px;right: 53px;z-index: 999;display: block;font-size: 18px;">×</button>
			<% } %>
		</span>
	</span>
<script type="text/javascript">
	$(document).ready(function () {
        var value = $("input[name='${name!}']").val();
        var urls = value.split("|");
        var label = "";
        for(var i=0; i<urls.length; i++){
          label= label+  decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+",";
        }
        if(label.length > 0){
            label = label.substr(0, label.length-1);
        }
        $("#${id!}").val(label);
    })
    function ${id!}FileDialogOpen() {
        var currentFileValues = $("input[name='${name!}']").val();
        jp.open({
            type: 2,
            area: ['800px', '300px'],
            title:"上传文件",
            auto:true,
            content: "${ctx}/tag/fileUpload?fileValues="+encodeURIComponent(currentFileValues)+"&uploadPath=${uploadPath!}"+"&type=${type!}"+"&readonly=${readonly!}"
            +"&fileNumLimit=${fileNumLimit!}"+"&fileSizeLimit=${fileSizeLimit!}"+"&allowedExtensions=${allowedExtensions!}"+"&deniedExtensions=${deniedExtensions!}",
            cancel: function(index, layero){
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var fileNames =iframeWin.contentWindow.getUploadFileNames();//调用保存事件
                var fileValues =iframeWin.contentWindow.getUploadFileValues();//调用保存事件
                $("#${id!}").val(fileNames);
                $("input[name='${name!}']").val(fileValues);
            }
        });
    }
	//清除
	$("#${id}DelButton").click(function(){
		$("#${id!}").val('');
		$("input[name='${name!}']").val('');
	});
</script>
