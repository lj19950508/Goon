	<input id="${id}Id" name="${name}"  type="hidden" value="${value!}"/>

	<span class="jp-input-search jp-input-search-enter-button jp-input-affix-wrapper">
		<input id="${id}Name"  name="${labelName }" ${allowInput!false ?'':'readonly="readonly"'}  type="text" value="${labelValue!}" data-msg-required="${dataMsgRequired!}" placeholder="${placeholder!('请选择'+title!)}"
			   class="${class!'jp-input'}" style="${cssStyle!}"/>
		<span class="jp-input-suffix">
			<button type="button"   id="${id}Button" class="jp-btn jp-input-search-button jp-btn-primary  ${disabled!} ${hideBtn!'false'=='true' ? 'hide' : ''}"><i class="jp-icon jp-icon-search"></i></button>
			  <% if(allowClear!'false' == 'true'){ %>
	             	 <button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute;top: 2px;right: 53px;z-index: 999;display: block;font-size: 18px;">×</button>

	            <% } %>
		</span>
	</span>

<script type="text/javascript">
$(document).ready(function(){
	$("#${id}Button, #${id}Name").click(function(){
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		
		top.layer.open({
		    type: 2,  
		    area: ['800px', '500px'],
		    title:"${title!}",
		    auto:true,
		    name:'friend',
		    content: "${ctx}/tag/gridselect?url="+encodeURIComponent(encodeURIComponent("${url}"))+"&fieldLabels="+encodeURIComponent("${fieldLabels}")+"&fieldKeys="+encodeURIComponent("${fieldKeys}")+"&searchLabels="+encodeURIComponent("${searchLabels!}")+"&searchKeys="+encodeURIComponent("${searchKeys!}")+"&isMultiSelected=${isMultiSelected!false}",
		    btn: ['确定', '关闭'],
		    yes: function(index, layero){
		    	 var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		    	 var items = iframeWin.getSelections();
		    	 if(items == ""){
			    	jp.warning("必须选择一条数据!");
			    	return;
		    	 }
		    	 var ids = [];
		    	 var names = [];
		    	 for(var i=0; i<items.length; i++){
		    		 var item = items[i];
		    		 ids.push(item${fn.substring(name, fn.lastIndexOf(name, '.'), fn.length(name))});
		    		 names.push(item${fn.substring(labelName, fn.lastIndexOf(labelName, '.'), fn.length(labelName))})
		    	 }
		    	 $("#${id}Id").val(ids.join(","));
		    	 $("#${id}Name").val(names.join(","));
				var callback = eval("${callback!}");
				if(typeof callback == "function"){
					var items = iframeWin.getSelections();
					callback(items);
				}
				 top.layer.close(index);//关闭对话框。
			  },
			  cancel: function(index){ 
		       }
		}); 
	})
	$("#${id}DelButton").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 清除	
		$("#${id}Id").val("");
		$("#${id}Name").val("");
		$("#${id}Name").focus();

	});
})
</script>
