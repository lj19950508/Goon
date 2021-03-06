<script>
    var pageObject = {
        tableRef:null,
        initTable:function(){
            var option = {
                treeShowField: 'name',
                parentIdField: 'parent.id',
                url: "${r'${ctx}'}/${moduleName}/${entityName}/list",
                onPostBody: function() {
                    var columns = $("#${entityName}Table").bootstrapTable('getOptions').columns
                    if (columns && columns[0][1].visible) {
                        $("#${entityName}Table").treegrid({
                            treeColumn: 1,
                            onChange: function() {
                                $("#menuTable").bootstrapTable('resetView')
                            }
                        })
                    }
                },
                columns: [
                    {checkbox: true},
                    {
                        field: 'name',
                        title: '${entityDesc}名称',
                        width:'100'
                    },
                    {
                        field: 'code',
                        title: '${entityDesc}编码',
                        width:'100'
                    }
                    <#list items as item>
                    <#if item.listLength!=0>
                    ,
                    {
                        <#if item.sort==true>
                        sortable:true,
                        </#if>
                        <#if item.dictCode?exists>
                        formatter:function (val,row,index) {
                        <#if item.formType==6 >
                            let valArr = val.split(',');
                            let dict = ${r'${'}dict.getJSON('${item['dictCode']}')}
                            let valInfo= [];

                            for(let item in dict){
                                for(let val in valArr){
                                    if ("[" + dict[item].value + "]"==valArr[val]) {
                                        valInfo.push(dict[item].name);
                                    }
                                }
                            }
                            return valInfo.join(',');
                        </#if>
                        <#if item.formType!=6>
                            let dict = ${r'${'}dict.getJSON('${item['dictCode']}')}
                            for(let item in dict){
                                if(dict[item].value===parseInt(val)){
                                   return dict[item].name
                                }
                            }
                            return '';
                        </#if>

                        },
                         <#else>
                        formatter:function (val,row,index) {
                        <#if item.formType==10 >
                            if(val){
                                let arr = val.split(',');
                                let html = '';
                                for(let i = 0 ; i < arr.length ; i++){
                                    let link = arr[i];
                                    let index = link.lastIndexOf("/")
                                    let name = link.substring(index+1,link.length);
                                    html+='<a target="_blank" href="'+link+'">'+name+'</a>'
                                    if(arr.length!=i-1){
                                        html+='</br>'
                                    }
                                }
                                return html;
                            }
                        </#if>
                       <#if item.formType!=10 >
                           return val;
                        </#if>
                        },
                        </#if>
                        field: '${item["itemName"]}<#if item.formType==9>.name</#if>',
                        title: '${item["itemDesc"]}',
                        width:${item["listLength"]}
                    }
                    </#if>
                    </#list>
                    ,{
                        field: 'operate',
                        title: '操作',
                        align: 'center',
                        events: {
                            'click .add': (e, value, row, index)=> {
                                this.add(row.id);
                            },
                            'click .view': (e, value, row, index)=> {
                                this.view(row.id);
                            },
                            'click .edit': (e, value, row, index)=> {
                                this.edit(row.id)
                            },
                            'click .del':  (e, value, row, index)=> {
                                this.del(row.id);
                            },
                        },
                        formatter:  function operateFormatter(value, row, index) {
                            var buttons = [];
                            buttons.push('<div class="btn-group" role="group" aria-label="Basic example">')
                            ${r'<@'}if(shiro.hasPermission("${moduleName?upper_case}:${entityName?upper_case}:ADD") ){  ${r'@>'}
                            buttons.push('<button class="add btn btn-primary btn-sm"><i class="fa fa-plus"></i></button>');
                            ${r'<@ } @>'}
                            ${r'<@'}if(shiro.hasPermission("${moduleName?upper_case}:${entityName?upper_case}:EDIT") ){  ${r'@>'}
                            buttons.push('<button class="edit btn btn-success btn-sm"><i class="fa fa-edit"></i></button>');
                            ${r'<@ } @>'}
                            ${r'<@'} if(shiro.hasPermission("${moduleName?upper_case}:${entityName?upper_case}:VIEW") ){ ${r'@>'}
                            buttons.push('<button class="view btn btn-default btn-sm"><i class="fa fa-info-circle"></i></button>');
                            ${r'<@ } @>'}
                            ${r'<@'} if(shiro.hasPermission("${moduleName?upper_case}:${entityName?upper_case}:DEL") ){ ${r'@>'}
                            buttons.push('<button class="del btn btn-danger btn-sm"><i class="fa fa-remove"></i></button>');
                            ${r'<@ } @>'}
                            buttons.push('</div>')
                            buttons = buttons.join("")
                            return buttons;
                        }
                    }
                ],
            }
            $.extend(true,option,defaultTreeTableOption,option)
            this.tableRef = $('#${entityName}Table').bootstrapTable(option);
            this.startAllLisener();
            // 监听事件
        },
        refresh:function(){
            this.tableRef.bootstrapTable('refresh');
        },

        getIdSelections:function(){
            return $.map(this.tableRef.bootstrapTable('getSelections'), function (row) {
                return row.id
            });
        },
        add:function(parentId){
            if(parentId!=null){
                go_into("${r'${ctx}'}/${moduleName}/${entityName}/form/add?parentId="+parentId);
            }else{
                go_into("${r'${ctx}'}/${moduleName}/${entityName}/form/add");
            }

        },
        view:function(id){
            go_into("${r'${ctx}'}/${moduleName}/${entityName}/form/view?id="+id);
        },
        edit:function(id){
            go_into("${r'${ctx}'}/${moduleName}/${entityName}/form/edit?id="+id);
        },
        del:function(id){
            let idarr = [];
            if(Array.isArray(id)){
                idarr=id;
            }else{
                idarr.push(id)
            }
            var index = layer.confirm('确定删除记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $req.post("${r'${ctx}'}/${moduleName}/${entityName}/delete",{id:idarr},function(res){
                    pageObject.refresh();
                    layer.close(index)
                })
            });
        },
        <#if excel==true>
        export:function(id){
            let idarr = [];
            if (Array.isArray(id)) {
                idarr = id;
            } else {
                idarr.push(id)
            }
            $.download('${r'${ctx}'}/${moduleName}/${entityName}/excel/export',{id:idarr});
        },
        import:function(){
            $req.upload('${r'${ctx}'}/${moduleName}/${entityName}/excel/import',$("#excelFile")[0]);
        },
        selectFile:function(){
            $("#excelFile").trigger("click");
        },
        template:function(){
            $.download('${r'${ctx}'}/${moduleName}/${entityName}/excel/template');
        },
        </#if>
        startAllLisener:function(){
            $("#search").click("click", function() {// 绑定查询按扭
                pageObject.refresh();
            });

            $("#clear").click("click", function() { //绑定重置按钮
                //重置表单
                $("#searchForm")[0].reset();
                pageObject.refresh();
            });
            $("#searchForm input").bind('keypress',function(e){
                if(e.keyCode=="13"){
                    pageObject.refresh();
                }
            })
            this.tableRef.on('check.bs.table uncheck.bs.table load-success.bs.table check-all.bs.table uncheck-all.bs.table',()=>{
                let selectLength = this.tableRef.bootstrapTable('getSelections').length;
                $('#remove').prop('disabled', !selectLength);
                if(selectLength==1){
                    $('#edit').prop('disabled', false);
                }else{
                    $('#edit').prop('disabled', true);
                }
            });
        }
    };
    $(function () {
        pageObject.initTable();
    })


</script>