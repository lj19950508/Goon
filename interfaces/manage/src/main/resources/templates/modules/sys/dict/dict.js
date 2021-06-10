<script>
var pageObject = {
    tableRef: null,
    dictTableRef:null,
    dictId:null,
    initDictTable: function () {
        let option = {
            url: "${ctx}/sys/dict/info",
            pagination:false,
            onClickCell: function(field, value, row, $element) {
                $element.attr('contenteditable', true);
                $element.blur(function() {
                    let index = $element.parent().data('index');
                    let tdValue = $element.html();
                    pageObject.dictTableRef.bootstrapTable('updateCell', {
                        index: index,       //行索引
                        field: field,       //列名
                        value: tdValue        //cell值
                    })
                })
            },
            queryParams:function(params) {
               params.id = pageObject.dictId;
               return params;
            },
            responseHandler: function(res) {
                return {"rows": res.data.dictItems};
            },
            columns: [
                {
                    field: 'name',
                    title: '字典项描述',
                }
                , {
                    field: 'value',
                    title: '字典项值',
                },
                {
                    field: 'sort',
                    title: '排序',
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                        'click .del': (e, value, row, index) => {
                            this.delDict(index);
                        },

                    },
                    formatter: function operateFormatter(value, row, index) {
                        var buttons = [];
                        buttons.push('<div class="btn-group" role="group" aria-label="Basic example">')
                        buttons.push('<button class="del btn btn-danger btn-sm"><i class="fa fa-remove"></i></button>');
                        buttons.push('</div>')
                        buttons = buttons.join("")
                        return buttons;
                    }
                }
            ],
        }
        $.extend(true, option,defaultSimpleTableOption,option)
        this.dictTableRef = $('#dictItemTable').bootstrapTable(option);
    },
    initTable: function () {
        let option = {
            url: "${ctx}/sys/dict/page",
            columns: [
                {checkbox: true},
                {
                    field: 'code',
                    title: '字典编码',
                }
                , {
                    field: 'name',
                    title: '字典描述',
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                        'click .view': (e, value, row, index) => {
                            this.view(row.id);
                        },
                        'click .edit': (e, value, row, index) => {
                            this.edit(row.id)
                        },
                        'click .del': (e, value, row, index) => {
                            this.del(row.id);
                        },
                        'click .dict': (e, value, row, index) => {
                            this.showDictItem(row.id)
                        },
                    },
                    formatter: function operateFormatter(value, row, index) {
                        var buttons = [];
                        buttons.push('<div class="btn-group" role="group" aria-label="Basic example">')
                        <@ if(shiro.hasPermission("SYS:DICT:EDIT") ){ @>
                        buttons.push('<button class="edit btn btn-success btn-sm"><i class="fa fa-edit"></i></button>');
                        <@ } @>
                        <@ if(shiro.orPermission("SYS:DICT:VIEW", "SYS:DICT:ADD", "SYS:DICT:EDIT")){ @>
                        buttons.push('<button class="dict btn btn-info btn-sm"><i class="fa fa-tasks"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("SYS:DICT:VIEW") ){ @>
                        buttons.push('<button class="view btn btn-default btn-sm"><i class="fa fa-info-circle"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("SYS:DICT:DEL") ){ @>
                        buttons.push('<button class="del btn btn-danger btn-sm"><i class="fa fa-remove"></i></button>');
                        <@ } @>
                        buttons.push('</div>')
                        buttons = buttons.join("")
                        return buttons;
                    }
                }
            ],
        }
        $.extend(true, option,defaultTableOption,option)
        this.tableRef = $('#dictTable').bootstrapTable(option);
        this.startAllLisener();
        // 监听事件
    },
    refresh: function () {
        this.tableRef.bootstrapTable('refresh');
    },

    getIdSelections: function () {
        return $.map(this.tableRef.bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    },
    add: function () {
        go_into("${ctx}/sys/dict/form/add");
    },
    view: function (id) {
        go_into("${ctx}/sys/dict/form/view?id=" + id);
    },
    edit: function (id) {
        go_into("${ctx}/sys/dict/form/edit?id=" + id);
    },
    addDict:function(){
        var arr = this.dictTableRef.bootstrapTable("getData");
        if(arr.length==0){
            this.dictTableRef.bootstrapTable('append', {value:0,name:"双击编辑",sort:0})
        }else{
            let prvItem = arr[arr.length-1]
            this.dictTableRef.bootstrapTable('append', {value:prvItem.value+1,name:prvItem.name,sort:prvItem.sort+1})
        }
        this.dictTableRef.bootstrapTable('scrollTo', 'bottom')
    },
    saveDict:function(){
        var arr = this.dictTableRef.bootstrapTable("getData");
        if(this.validateDict(arr)){
            // now.
            var dictItems={};
            for(let i = 0; i <arr.length;i++){
                dictItems['dictItems['+i+'].name']  = arr[i].name;
                dictItems['dictItems['+i+'].value']  = arr[i].value;
                dictItems['dictItems['+i+'].sort']  = arr[i].sort;
            }
            dictItems.id = pageObject.dictId

            $req.post("${ctx}/sys/dict/saveDictItem",dictItems,function(res){
                pageObject.refresh();
            },{success:true,error:true})
            //提交表单
        }
    },
    validateDict:function(arr){
        for (var i = 0; i < arr.length - 1; i++) {
            for (var j = i + 1; j < arr.length; j++) {
                if (arr[i].value == arr[j].value) {
                    layer.error("字典值不能重复");
                    return false;
                }
            }
        }
        return true;
    },
    delDict:function(index){
        this.dictTableRef.bootstrapTable("remove",{
            field:'$index',
            values:[index]
        })
    },
    del: function (id) {
        let idarr = [];
        if (Array.isArray(id)) {
            idarr = id;
        } else {
            idarr.push(id)
        }
        var index = layer.confirm('确定删除记录？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $req.post("${ctx}/sys/dict/delete", {id: idarr}, function (res) {
                pageObject.refresh();
                layer.close(index)
            })
        });
    },
    showDictItem: function (id) {
        $("#left").attr("class", "col-sm-12");
        $("#right").show(100);
        this.dictId=id;
        if(this.dictTableRef==null){
            this.initDictTable(id);
        }else{
            this.dictTableRef.bootstrapTable('refresh',{query:{id:id}});
        }
    },
    hideDictItem: function () {
        $("#left").attr("class", "col-sm-24");
        $("#right").hide();
    },
    startAllLisener: function () {
        $("#search").click("click", function () {// 绑定查询按扭
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
        this.tableRef.on('check.bs.table uncheck.bs.table load-success.bs.table check-all.bs.table uncheck-all.bs.table', () => {
            let selectLength = this.tableRef.bootstrapTable('getSelections').length;
            $('#remove').prop('disabled', !selectLength);
            if (selectLength == 1) {
                $('#edit').prop('disabled', false);
            } else {
                $('#edit').prop('disabled', true);
            }
        });
    }
};
$(function () {
    pageObject.initTable();

})


</script>