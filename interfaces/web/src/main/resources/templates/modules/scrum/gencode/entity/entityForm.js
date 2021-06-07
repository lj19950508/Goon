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
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="itemName" placeholder="字段名">'
                    }
                }
                , {
                    //输入
                    field: 'itemDesc',
                    title: '字段描述',
                    width:150,
                    formatter:function(value, row, index){
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="itemDesc" placeholder="字段描述">'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'formType',
                    title: '表单组件',
                    formatter:function(value, row, index){
                        return '<select onblur="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="formType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                    '<option value="1" label="文本框">文本框</option>'+
                                    '<option value="2" label="文本域">文本域</option>'+
                                    '<option value="3" label="富文本">富文本</option>'+
                                    '<option value="4" label="下拉">下拉</option>'+
                                    '<option value="5" label="单选">单选</option>'+
                                    '<option value="6" label="复选">复选</option>'+
                                    '<option value="7" label="日期">日期</option>'+
                                    '<option value="8" label="数字">数字</option>'+
                                    '<option value="9" label="滑块">滑块</option>'+
                               '</select>'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'itemType',
                    title: 'java类型',
                    formatter:function(value, row, index){
                        return '<select onblur="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="itemType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                    '<option value="String" label="String">String</option>'+
                                    '<option value="Integer" label="Integer">Integer</option>'+
                                    '<option value="BigDecimal" label="BigDecimal">BigDecimal</option>'+
                                    '<option value="boolean" label="boolean">boolean</option>'+
                                    '<option value="Long" label="Long">Long</option>'+
                                    '<option value="Date" label="Date">Date</option>'+
                                    '<option value="Double" label="Double">Double</option>'+
                                    '<option value="Float" label="Float">Float</option>'+
                                    '<option value="LocalDate" label="LocalDate">LocalDate</option>'+
                                    '<option value="LocalDateTime" label="LocalDateTime">LocalDateTime</option>'+
                               '</select>'
                    }
                },
                {
                    width:100,
                    field: 'sqlType',
                    title: 'sql类型',
                    formatter:function(value, row, index){
                        return '<select onblur="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="sqlType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                  '<option value="varchar" label="varchar">varchar</option>'+
                                  '<option value="int" label="int">int</option>'+
                                  '<option value="tinyint" label="tinyint">tinyint</option>'+
                                  '<option value="bit" label="bit">bit</option>'+
                                  '<option value="bigint" label="bigint">bigint</option>'+
                                  '<option value="double" label="double">double</option>'+
                                  '<option value="float" label="float">float</option>'+
                                  '<option value="decimal" label="decimal">decimal</option>'+
                                  '<option value="char" label="char">char</option>'+
                                  '<option value="datetime" label="datetime">datetime</option>'+
                                  '<option value="tinyblob" label="tinyblob">tinyblob</option>'+
                                  '<option value="blob" label="blob">blob</option>'+
                                  '<option value="longblob" label="longblob">longblob</option>'+
                                  '<option value="tinytext" label="tinytext">tinytext</option>'+
                                  '<option value="text" label="text">text</option>'+
                                  '<option value="longtext" label="longtext">longtext</option>'+
                               '</select>'
                    }
                },
                {
                    //输入
                    field: 'sqlLength',
                    title: 'sql长度(0不显示)',
                    width:150,
                    formatter: function (value, row, index) {
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="' + value + '" class="form-control" name="sqlLength" placeholder="字段长度">'
                    }
                },
                {
                    //不查询 0
                    //文本框 1
                    //下拉框2
                    //单选框3
                    //复选框4
                    //日期5
                    //数字框
                    //数字范围框
                    width:100,
                    field: 'queryType',
                    title: '查询组件',
                    formatter:function(value, row, index){
                    return '<select onblur="pageFormObject.changeData('+ index +', this);"    data-width="100"   name="queryType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                              '<option value="0" label="不查询">不查询</option>'+
                              '<option value="1" label="文本框">文本框</option>'+
                              '<option value="2" label="下拉框">下拉框</option>'+
                              '<option value="3" label="单选框">单选框</option>'+
                              '<option value="4" label="复选框">复选框</option>'+
                              '<option value="5" label="日期">日期</option>'+
                              '<option value="6" label="数字">数字</option>'+
                              '<option value="7" label="数字范围">数字范围</option>'+
                           '</select>'
                    }
                },
                {
                    width:100,
                    field: 'queryExp',
                    title: '查询表达式',
                    formatter:function(value, row, index){
                    return '<select onblur="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="queryExp" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                 '<option value="0" label="相等">相等</option>'+
                                 '<option value="1" label="相似">相似</option>'+
                                 '<option value="2" label="左相似">左相似</option>'+
                                 '<option value="3" label="右相似">右相似</option>'+
                                 '<option value="4" label="大于">大于</option>'+
                                 '<option value="5" label="小于">小于</option>'+
                                 '<option value="6" label="大于等于">大于等于</option>'+
                                 '<option value="7" label="小于等于">小于等于</option>'+
                                 '<option value="8" label="之间">之间</option>'+
                           '</select>'
                    }

                },
                {
                    //输入 0为不显示
                    field: 'listLength',
                    title: '列宽',
                    width:100,
                    formatter:function(value, row, index){
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="listLength" placeholder="列宽">'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'isMust',
                    title: '必填',
                    formatter:function(value, row, index){
                        return '<div class="custom-control custom-switch">' +
                            '       <input onblur="pageFormObject.changeData('+ index +', this);"  type="checkbox" class="custom-control-input" name="isMust" >' +
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
                            '       <input onblur="pageFormObject.changeData('+ index +', this);"  type="checkbox" class="custom-control-input" name="isUnique" >' +
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
                        return '<div  class="custom-control custom-switch">' +
                            '       <input onblur="pageFormObject.changeData('+ index +', this);"  type="checkbox" class="custom-control-input" name="isSort" >' +
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

                        return '<select onblur="pageFormObject.changeData('+ index +', this);"   data-width="150"   name="dictId" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
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
        this.tableRef.bootstrapTable('append', [{itemName:'',itemDesc:'',formType:0,itemType:0,sqlType:0,sqlLength:'',queryType:0,queryExp:0,listLength:0,isMust:0,isUnique:0,isSort:0,dictId:''}]);
        $(".item-select").selectpicker('show')
    },
    //保存临时数据
    changeData(index,obj){
        var value = $(obj).val();
        var name = $(obj).attr('name');
        var row = this.tableRef.bootstrapTable('getData')[index];
        row[name] = value;
        this.tableRef.bootstrapTable('updateRow',{index: index, row: row});
        $(".item-select").selectpicker('show')

    },
    del:function(row,index){
        let totals = this.tableRef.bootstrapTable('getData').length;
        if(totals>1){
            this.tableRef.bootstrapTable('remove', {field: '$index', values: [index]})
            $(".item-select").selectpicker('show')
        }else{
            layer.error("至少保留一项数据");
        }
    }
};

$(function () {
    pageFormObject.init()
})

</script>