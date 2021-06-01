<script>
var pageObject = {
    tableRef:null,
        initTable:function(){
        var option = {
            url: "${ctx}/sys/param/page",
            columns: [
                {checkbox: true,},
                {
                    field: 'code',
                    title: '编码',
                    sortable:true
                }
                ,{
                    field: 'name',
                    title: '描述',

                },
                {
                    field: 'createBy.nickname',
                    title: '创建者',
                    width:100
                },       {
                    field: 'createTime',
                    title: '创建时间',
                    width:100
                },
                {
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
                        <@ if(shiro.hasPermission("SYS:PARAM:EDIT") ){ @>
                        buttons.push('<button class="edit btn btn-success btn-sm"><i class="fa fa-edit"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("SYS:PARAM:VIEW") ){ @>
                        buttons.push('<button class="view btn btn-default btn-sm"><i class="fa fa-info-circle"></i></button>');
                        <@ } @>
                        <@ if(shiro.hasPermission("SYS:PARAM:DEL") ){ @>
                        buttons.push('<button class="del btn btn-danger btn-sm"><i class="fa fa-remove"></i></button>');
                        <@ } @>
                        buttons.push('</div>')
                        buttons = buttons.join("")
                        return buttons;
                    }
                }
            ],
        }
        $.extend(true,option,defaultTableOption,option)
        this.tableRef = $('#paramTable').bootstrapTable(option);
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
        go_into("${ctx}/sys/param/form/add");
    },
    view:function(id){
        go_into("${ctx}/sys/param/form/view?id="+id);
    },
    edit:function(id){
        go_into("${ctx}/sys/param/form/edit?id="+id);
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
            $req.post("${ctx}/sys/param/delete",{id:idarr},function(res){
                pageObject.refresh();
                layer.close(index)
            })
        });


    },
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