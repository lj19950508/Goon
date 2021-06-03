<script>
var pageFormObject={
    tableRef: null,
    startAllLisener:function(){

        $form.submit("#entityForm",function(data){
            if (data.success) {
                go_into("${ctx}/gencode/entity");
            }
        },null,{error:true})
        <@ if(mode=='view'){ @>
        $('#gencodeForm').find('input,textarea,select').prop('disabled',true);
        $('#submit').prop('disabled',true);
        <@ } @>
    },
    initFieldTable:function(){
        let option = {
            pagination: false,
            sidePagination: "client",
            columns: [
                {
                    //输入
                    field: 'itemName',
                    title: '字段名',
                    formatter:function(value, row, index){

                    }
                }
                , {
                    //输入
                    field: 'itemDesc',
                    title: '字段描述',
                },
                {
                    //下拉
                    field: 'formType',
                    title: '表单组件',
                },
                {
                    //下拉
                    field: 'formType',
                    title: '表单组件',
                },
                {
                    //下拉
                    field: 'itemType',
                    title: 'JAVA类型',
                },
                {
                    //下拉
                    field: 'sqlType',
                    title: 'sql类型',
                },
                {
                    //下拉
                    field: 'queryType',
                    title: '查询组件类型',
                },
                {
                    //下拉
                    field: 'queryExp',
                    title: '查询表达式',
                },
                {
                    //输入
                    field: 'listLength',
                    title: '列长',
                },
                {
                    //toggle
                    field: 'isMust',
                    title: '必填',
                },
                {
                    //toggle
                    field: 'isUnique',
                    title: '唯一',
                },
                {
                    //toggle
                    field: 'isSort',
                    title: '排序',
                },
                {
                    //下拉
                    field: 'dictId',
                    title: '关联字典',
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                        'click .del': (e, value, row, index) => {
                            this.del(row,index);
                        },
                    },
                    formatter: function operateFormatter(value, row, index) {
                        var buttons = [];
                        buttons.push('<button class="del btn btn-danger btn-sm"><i class="fa fa-remove"></i></button>');
                        buttons = buttons.join("")
                        return buttons;
                    }
                }
            ],
        }

        $.extend(true, option,defaultTableOption,option)
        this.tableRef = $('#entityItemTable').bootstrapTable(option);
    },
    init:function () {
        this.initFieldTable()
        this.startAllLisener();
        this.getData();
    },
    getData:function(){
        this.tableRef.bootstrapTable('append', [{itemName:'测试'}]);
    }
    del:function(row,index){

    }
};

$(function () {
    pageFormObject.init()
})

</script>