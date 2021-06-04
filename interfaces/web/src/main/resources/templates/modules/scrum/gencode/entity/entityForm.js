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
                    width:150,
                    formatter:function(value, row, index){
                        return '<input type="text"  value="'+value+'" class="form-control" name="itemName" placeholder="字段名">'
                    }
                }
                , {
                    //输入
                    field: 'itemDesc',
                    title: '字段描述',
                    width:150,
                    formatter:function(value, row, index){
                        return '<input type="text"  value="'+value+'" class="form-control" name="itemDesc" placeholder="字段描述">'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'formType',
                    title: '表单组件',
                    formatter:function(value, row, index){
                        return '<select  data-width="auto" name="name" class="form-control" data-style="btn-default" data-live-search="true">'+
                                    '<option value="1" label="1">下拉框</option>'+
                                    '<option value="2" label="2">文件选择器</option>'+
                               '</select>'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'itemType',
                    title: 'java类型',
                    formatter:function(value, row, index){
                        return '<select  data-width="auto"   name="name" class="form-control" data-style="btn-default" data-live-search="true">'+
                                    '<option value="1" label="1">String</option>'+
                                    '<option value="2" label="2">Integer</option>'+
                                    '<option value="2" label="2">Area</option>'+
                               '</select>'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'sqlType',
                    title: 'sql类型',
                    formatter:function(value, row, index){
                        return '<select  data-width="auto"   name="name" class="form-control" data-style="btn-default" data-live-search="true">'+
                            '<option value="1" label="1">varchar</option>'+
                            '<option value="2" label="2">char</option>'+
                            '<option value="2" label="2">bigInt</option>'+
                            '</select>'
                    }
                },
                {
                    //输入
                    field: 'sqlLength',
                    title: 'sql长度',
                    width:150,
                    formatter: function (value, row, index) {
                        return '<input type="text"  value="' + value + '" class="form-control" name="itemDesc" placeholder="字段长度">'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'queryType',
                    title: '查询组件',
                    formatter:function(value, row, index){
                        return '<select  data-width="auto"   name="name" class="form-control" data-style="btn-default" data-live-search="true">'+
                            '<option value="1" label="1">下拉框</option>'+
                            '<option value="2" label="2">文件选择器</option>'+
                            '</select>'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'queryExp',
                    title: '查询表达式',
                    formatter:function(value, row, index){
                        return  '<select   data-width="auto"  name="name" class="form-control" data-style="btn-default" data-live-search="true">'+
                                    '<option value="1" label="1">不相等</option>'+
                                    '<option value="2" label="2">相等</option>'+
                                '</select>'
                    }

                },
                {
                    //输入
                    field: 'listLength',
                    title: '列宽',
                    width:100,
                    formatter:function(value, row, index){
                        return '<input type="text"  value="'+value+'" class="form-control" name="itemDesc" placeholder="列宽">'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'isMust',
                    title: '必填',
                    formatter:function(value, row, index){
                        return '<div class="custom-control custom-switch">' +
                            '       <input type="checkbox" class="custom-control-input" id="customSwitch1">' +
                            '       <label class="custom-control-label" for="customSwitch1"></label>' +
                            '   </div>'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'isUnique',
                    title: '唯一',
                    formatter:function(value, row, index){
                        return '<div class="custom-control custom-switch">' +
                            '       <input type="checkbox" class="custom-control-input" id="customSwitch1">' +
                            '       <label class="custom-control-label" for="customSwitch1"></label>' +
                            '   </div>'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'isSort',
                    title: '排序',
                    formatter:function(value, row, index){
                        return '<div class="custom-control custom-switch">' +
                            '       <input type="checkbox" class="custom-control-input" id="customSwitch1">' +
                            '       <label class="custom-control-label" for="customSwitch1"></label>' +
                            '   </div>'
                    }
                },
                {
                    //下拉
                    width:150,
                    field: 'dictId',
                    title: '关联字典',
                    formatter:function(value, row, index){

                        return '<select  data-width="auto"   name="name" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                            '<option value="1" label="1">非常厉害的字段</option>'+
                            '<option value="2" label="2">2</option>'+
                            '</select>'
                    }
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
                        buttons.push('<button type="button" class="del btn btn-danger btn-sm"><i class="fa fa-remove"></i></button>');
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
        if(true){
            this.appendNewData()
        }else{

        }
        //
    },
    appendNewData(){
        //保存已有数据
        this.
        this.tableRef.bootstrapTable('append', [{itemName:'',itemDesc:'',formType:'',itemType:'',sqlType:'',sqlLength:'',queryType:'',queryExp:'',listLength:'',isMust:0,isUnique:0,isSort:0,dictId:''}]);
        // this.tableRef.bootstrapTable('insertRow',{index:1,row:{itemName:'',itemDesc:'',formType:'',itemType:'',sqlType:'',sqlLength:'',queryType:'',queryExp:'',listLength:'',isMust:0,isUnique:0,isSort:0,dictId:''}} );
        //bug1.刷新后值丢失
        //bug2.table列宽没有适应
        $(".item-select").selectpicker('show')
    },
    //保存临时数据
    tableTempSave(){

    },
    del:function(row,index){
        let totals = this.tableRef.bootstrapTable('getData').length;
        if(totals>1){
            this.tableRef.bootstrapTable('remove', {field: '$index', values: [index]})
        }else{
            layer.error("至少保留一项数据");
        }
    }
};

$(function () {
    pageFormObject.init()
})

</script>