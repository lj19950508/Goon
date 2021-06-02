<%--树形组件--%>
<input id="${id}Id" name="${name}" value="${value!}"  type="hidden"    />
<div class="input-group ">

	<input onfocus="this.blur()" type="text" class="form-control"  id="${id}Name" name="${labelName!}" placeholder="${placeholder!}" ${required!true==true?'':'required="required"'} value="${labelValue!}"  >
	<div class="input-group-append">
		<button class="btn btn-primary" type="button" id="${id}Button" ${disabled!}><i class="fa fa-search"></i></button>
	</div>
	<div class="invalid-feedback">请${placeholder!}!</div>

</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#${id}Button, #${id}Name").click(function(){
			// 是否限制选择，如果限制，设置为disabled
			if ($("#${id}Button").hasClass("disabled")){
				return true;
			}
			// 正常打开
			top.layer.open({
				type: 2,
				area: ['350px', '620px'],
				title:"选择${title}",
				ajaxData:{selectIds: $("#${id}Id").val()},
				content: "${ctx}/tag/treeselect?url="+encodeURIComponent("${url!}")+"&module=${module!}&checked=${checked!}&extId=${extId!}&isAll=${isAll!}&allowSearch=${allowSearch!}" ,
				btn: ['确定', '关闭']
				,yes: function(index, layero){ //或者使用btn1
					var tree = layero.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
					var ids = [], names = [], nodes = [];
					if ("${checked!}" == "true"){
						nodes = tree.getCheckedNodes(true);
					}else{
						nodes = tree.getSelectedNodes();
					}
					for(var i=0; i<nodes.length; i++) {
						<@ if((checked!false == true) &&(notAllowSelectParent!false == true)){ @>
						if (nodes[i].isParent){
							continue; // 如果为复选框选择，则过滤掉父节点
						}
						<@ } @>
						<@ if(notAllowSelectRoot!false != false){ @>
						if (nodes[i].level == 0){
							top.layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
							return false;
						}
						<@ } @>
						<@ if(notAllowSelectParent!false != false){ @>
						if (nodes[i].isParent){
							top.layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
							return false;
						}
						<@ } @>
						ids.push(nodes[i].id);
						names.push(nodes[i].name);
						<@ if(checked!false == false){ @>
						break; // 如果为非复选框选择，则返回第一个选择
						<@ } @>
					}

					$("#${id}Id").val(ids.join(",").replace(/u_/ig,""));
					$("#${id}Name").val(names.join(","));
					$("#${id}Name").focus();
					<@ if(callback!false != false){ @>
					var callback = '${callback == null ? "''" : callback}';
					if(callback instanceof Function){
						callback(ids);
					}
					<@ } @>
					top.layer.close(index);
				},
				cancel: function(index){
				}
			});

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
	})
</script>