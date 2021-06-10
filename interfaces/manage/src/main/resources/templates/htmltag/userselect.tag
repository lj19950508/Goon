<%--未启用--%>
	<input id="${id}Id" name="${name}"  type="hidden" value="${value!}" class="${class!}"/>
	<span class="jp-input-search jp-input-search-enter-button jp-input-affix-wrapper">
		<input class="jp-input ${class!}"  id="${id}Name" name="${labelName!}" ${allowInput!true==true?'':'readonly="readonly" style="cursor: pointer;"'}  type="text" value="${labelValue!}" data-msg-required="${dataMsgRequired!}" >
		<span class="jp-input-suffix">
			<button type="button"   id="${id}Button" class="jp-btn jp-input-search-button jp-btn-primary  ${disabled!} ${hideBtn!'false'=='true' ? 'hide' : ''}"><i class="jp-icon jp-icon-usergroup-add"></i></button>
			  <% if(allowClear!'false' == 'true'){ %>
	             	 <button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute; top: -1px; right: 53px; z-index: 999; display: block;">×</button>

	            <% } %>
		</span>
	</span>
<script type="text/javascript">
	$("#${id}Button, #${id}Name").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 正常打开	
		
		jp.openUserSelectDialog({isMultiSelect:${isMultiSelected!false? true:false},selectedId:$("#${id}Id").val(),selectedName:$("#${id}Name").val()},function(ids, names){
			$("#${id}Id").val(ids.replace(/u_/ig,""));
			$("#${id}Name").val(names);
			$("#${id}Name").focus();
		})
	
	});
	
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
</script>