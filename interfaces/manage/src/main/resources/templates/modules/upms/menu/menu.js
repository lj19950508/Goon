<script>
var pageObject = {
    tableRef:null,
    initTable:function(){
        var option = {
            treeShowField: 'name',
            parentIdField: 'parent.id',
            onPostBody: function() {
                var columns = $("#menuTable").bootstrapTable('getOptions').columns
                if (columns && columns[0][1].visible) {
                    $("#menuTable").treegrid({
                        treeColumn: 1,
                        onChange: function() {
                            $("#menuTable").bootstrapTable('resetView')
                        }
                    })
                }
            },
            url: "${ctx}/upms/menu/list",
            columns: [
                {checkbox: true,},
                {
                    field: 'name',
                    title: '菜单名称',
                    width:'300'
                },
                {
                    field: 'code',
                    title: '菜单编号',
                    width:'300'
                },
                {
                    field: 'createBy.nickname',
                    title: '创建者',
                    width:'300'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width:'300'

                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    cellStyle:{
                        css:{"overflow":"visible"}
                    },
                    class: 'text-nowrap',width:'120px',
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
                        <@ if(shiro.hasPermission("UPMS:MENU:ADD") ){ @>
                        buttons.push('<button class="add btn btn-primary btn-sm"><i class="fa fa-plus"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("UPMS:MENU:EDIT") ){ @>
                        buttons.push('<button class="edit btn btn-success btn-sm"><i class="fa fa-edit"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("UPMS:MENU:VIEW") ){ @>
                        buttons.push('<button class="view btn btn-default btn-sm"><i class="fa fa-info-circle"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("UPMS:MENU:DEL") ){ @>
                        buttons.push('<button class="del btn btn-danger btn-sm"><i class="fa fa-remove"></i></button>');
                        <@ } @>
                        buttons.push('</div>')
                        buttons = buttons.join("")
                        return buttons;
                    }
                }
            ],
        }
        //options合并覆盖default,最终结果给option
        $.extend(true,option,defaultTreeTableOption,option)
        this.tableRef = $('#menuTable').bootstrapTable(option);
        this.startAllLisener();
        // 监听事件
    },
    refresh:function(params){
        this.tableRef.bootstrapTable('refresh');
    },
    getIdSelections:function(){
        return $.map(this.tableRef.bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    },
    add:function(parentId){
        if(parentId!=null){
            go_into("${ctx}/upms/menu/form/add?parentId="+parentId);
        }else{
            go_into("${ctx}/upms/menu/form/add");
        }
    },
    view:function(id){
        console.log(id)
        go_into("${ctx}/upms/menu/form/view?id="+id);
    },
    edit:function(id){
        go_into("${ctx}/upms/menu/form/edit?id="+id);
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
            $req.post("${ctx}/upms/menu/delete",{id:idarr},function(res){
                pageObject.refresh();
                layer.close(index)
            })
        });
    },
    startAllLisener:function(){
        $("#search").click("click", function() {// 绑定查询按扭
            pageObject.refresh($("#searchForm").serializeJSON());
        });

        $("#clear").click("click", function() { //绑定重置按钮
            //重置表单
            $("#searchForm")[0].reset();
            pageObject.refresh($("#searchForm").serializeJSON());

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