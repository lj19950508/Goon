<script>
    var pageObject = {
        tableRef:null,
        //加入树形初始化todo    ------------------------
        ${relatedTree.entityName}TreeSearch: function (v) {
            var treeObj = $.fn.zTree.getZTreeObj("${relatedTree.entityName}Tree");
            treeObj.setting.async.otherParam = ['search', v];
            $("#${relatedTree.entityName}Tree").text("正在查询");
            treeObj.reAsyncChildNodes(null, "refresh", true, function () {
            });
        },
        init${relatedTree.entityName}Tree: function () {
            var setting = {
                async: {
                    enable: true,
                    autoParam: ['id'],
                    dataType: 'json',
                    type: 'get',
                    url: "${ctx}" + "/${relatedTree.moduleName}/${relatedTree.entityName}/list",
                    dataFilter: function (treeId, parentNode, responseData) {
                        return responseData.data;
                    }
                },
                data: {
                    key: {
                        url: "none"
                    },
                    simpleData: {
                        idKey: "id",
                        pIdKey: "parent.id",
                        enable: true,
                    }
                },
                check: {enable: false},
                callback: {
                    onClick: function (event, treeId, treeNode, clickFlag) {
                        $("#${relatedTree.entityName}Code").val(treeNode.code)
                        pageObject.refresh();
                    },
                    onAsyncSuccess: function (event, treeId, treeNode, msg) {
                        var treeObj = $.fn.zTree.getZTreeObj(treeId);
                        var nodes = treeObj.getNodes();
                        treeObj.expandNode(nodes[0], true, true, true);
                    }
                }
            }
            tree = $.fn.zTree.init($('#${relatedTree.entityName}Tree'), setting, null);
            //查找功能
            var to = false;
            $('#search-q').keyup(function () {
                if (to) {
                    clearTimeout(to);
                }
                to = setTimeout(function () {
                    var v = $('#search-q').val();
                    pageObject.${relatedTree.entityName}TreeSearch(v);
                }, 250);
            });
            $("#searchButton").click(function () {
                var v = $('#search-q').val();
                pageObject.${relatedTree.entityName}TreeSearch(v);
            })
        },
        initTable:function(){
            var option = {
                url: "${r'${ctx}'}/${moduleName}/${entityName}/page",
                columns: [
                    {checkbox: true,}
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
            $.extend(true,option,defaultTableOption,option)
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
        add:function(){
            go_into("${r'${ctx}'}/${moduleName}/${entityName}/form/add");
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