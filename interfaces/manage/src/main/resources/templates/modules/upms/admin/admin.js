<script>
var pageObject = {
    tableRef: null,
    deptTreeSearch: function (v) {
        var treeObj = $.fn.zTree.getZTreeObj("deptTree");
        treeObj.setting.async.otherParam = ['search', v];
        $("#deptTree").text("正在查询");
        treeObj.reAsyncChildNodes(null, "refresh", true, function () {
        });
    },
    initDeptTree: function () {
        var setting = {
            async: {
                enable: true,
                autoParam: ['id'],
                dataType: 'json',
                type: 'get',
                url: "${ctx}" + "/upms/dept/list",
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
                    //获得该节点下搜友的接单
                    $("#deptCode").val(treeNode.code)
                    pageObject.refresh();
                },
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    var nodes = treeObj.getNodes();
                    treeObj.expandNode(nodes[0], true, true, true);
                }
            }
        }
        tree = $.fn.zTree.init($('#deptTree'), setting, null);
        //查找功能
        var to = false;
        $('#search-q').keyup(function () {
            if (to) {
                clearTimeout(to);
            }
            to = setTimeout(function () {
                var v = $('#search-q').val();
                pageObject.deptTreeSearch(v);
            }, 250);
        });
        $("#searchButton").click(function () {
            var v = $('#search-q').val();
            pageObject.deptTreeSearch(v);
        })
    },
    initTable: function () {
        var option = {
            url: "${ctx}/upms/admin/page",
            columns: [
                {checkbox: true,},
                {
                    field: 'account',
                    title: '账号',
                    width: 200
                }
                , {
                    field: 'nickname',
                    title: '昵称',
                    width: 100
                },
                {
                    field: 'dept.name',
                    title: '所属部门',
                    width: 100
                },
                {
                    field: 'createBy.nickname',
                    title: '创建者',
                    width: 100
                }, {
                    field: 'createTime',
                    title: '创建时间',
                    width: 300
                },


                {
                    width: 300,
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
                    },
                    formatter: function operateFormatter(value, row, index) {
                        var buttons = [];
                        buttons.push('<div class="btn-group" role="group" aria-label="Basic example">')
                        <@ if(shiro.hasPermission("UPMS:ADMIN:EDIT") ){ @>
                        buttons.push('<button class="edit btn btn-success btn-sm"><i class="fa fa-edit"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("UPMS:ADMIN:VIEW") ){ @>
                        buttons.push('<button class="view btn btn-default btn-sm"><i class="fa fa-info-circle"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("UPMS:ADMIN:DEL") ){ @>
                        buttons.push('<button class="del btn btn-danger btn-sm"><i class="fa fa-remove"></i></button>');
                        <@ } @>
                        buttons.push('</div>')
                        buttons = buttons.join("")
                        return buttons;
                    }
                }
            ],
        }
        $.extend(true, option, defaultTableOption, option)
        this.tableRef = $('#adminTable').bootstrapTable(option);
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
        go_into("${ctx}/upms/admin/form/add");
    },
    view: function (id) {
        go_into("${ctx}/upms/admin/form/view?id=" + id);
    },
    edit: function (id) {
        go_into("${ctx}/upms/admin/form/edit?id=" + id);
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
            $req.post("${ctx}/upms/admin/delete", {id: idarr}, function (res) {
                pageObject.refresh();
                layer.close(index)
            })
        });


    },
    export:function(id){
        let idarr = [];
        if (Array.isArray(id)) {
            idarr = id;
        } else {
            idarr.push(id)
        }
        $.download('${ctx}/upms/admin/excel/export',{id:idarr});
    },
    import:function(){
        $req.upload('${ctx}/upms/admin/excel/import',$("#excelFile")[0]);
    },
    selectFile:function(){
        $("#excelFile").trigger("click");
    },
    template:function(){
        $.download('${ctx}/upms/admin/excel/template');
    },
    startAllLisener: function () {
        $("#search").click("click", function () {// 绑定查询按扭
            pageObject.refresh();
        });
        $("#searchForm input").bind('keypress', function (e) {
            if (e.keyCode == "13") {
                pageObject.refresh();
            }
        })

        $("#clear").click("click", function () { //绑定重置按钮
            //查询框滞空
            $("#searchForm")[0].reset();
            pageObject.refresh();
        });

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
    pageObject.initDeptTree();
    pageObject.initTable();
})


</script>