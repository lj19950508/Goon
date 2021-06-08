<script>
var pageFormObject={
    tableRef: null,
    formTypes:[
        {value:1,label:"文本框"},
        {value:2,label:"文本域"},
        {value:3,label:"富文本"},
        {value:4,label:"下拉"},
        {value:5,label:"单选"},
        {value:6,label:"复选"},
        {value:7,label:"日期"},
        {value:8,label:"数字"},
        {/*{value:9,label:"开关"}*/}
    ],
    //取决于form
    itemTypes:[
        {value:'String',label:"String"},
        {value:'Integer',label:"Integer"},
        {value:'BigDecimal',label:"BigDecimal"},
        {value:'boolean',label:"boolean"},
        {value:'Long',label:"Long"},
        {value:'Date',label:"Date"},
        {value:'Double',label:"Double"},
        {value:'Float',label:"Float"},
        {value:'LocalDate',label:"LocalDate"},
        {value:'LocalDateTime',label:"LocalDateTime"},
    ],
    //取决于itemTypes
    sqlTypes:[
        {value:'varchar',label:"varchar"},
        {value:'int',label:"int"},
        {value:'tinyint',label:"tinyint"},
        {value:'bit',label:"bit"},
        {value:'bigint',label:"bigint"},
        {value:'double',label:"double"},
        {value:'float',label:"float"},
        {value:'decimal',label:"decimal"},
        {value:'char',label:"char"},
        {value:'datetime',label:"datetime"},
        {value:'tinyblob',label:"tinyblob"},
        {value:'blob',label:"blob"},
        {value:'longblob',label:"longblob"},
        {value:'tinytext',label:"tinytext"},
        {value:'text',label:"text"},
        {value:'longtext',label:"longtext"},
    ],
    //取决于form
    queryTypes:[
        {value:'0',label:"不查询"},
        {value:'1',label:"文本框"},
        {value:'2',label:"下拉框"},
        {value:'3',label:"单选框"},
        {value:'4',label:"复选框"},
        {value:'5',label:"日期"},
        {value:'6',label:"数字"},
        {value:'7',label:"数字范围"},
    ],
    //取决于queryTypes
    queryExps:[
        {value:'0',label:"相等"},
        {value:'1',label:"相似"},
        {value:'2',label:"左相似"},
        {value:'3',label:"右相似"},
        {value:'4',label:"大于"},
        {value:'5',label:"小于"},
        {value:'6',label:"大于等于"},
        {value:'7',label:"小于等于"},
        {value:'8',label:"之间"},

    ],
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
                    title: '字段名*',
                    width:150,
                    formatter:function(value, row, index){
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="itemName" placeholder="字段名">'
                    }
                }
                , {
                    //输入
                    field: 'itemDesc',
                    title: '字段描述*',
                    width:150,
                    formatter:function(value, row, index){
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="itemDesc" placeholder="字段描述">'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'formType',
                    title: '表单组件*',
                    formatter:function(value, row, index){
                        let options ='';
                        pageFormObject.formTypes.forEach(item=>{
                            if(item.value==value){
                                options +='<option selected value="'+item.value+'">'+item.label+'</option>'
                            }else{
                                options +='<option  value="'+item.value+'">'+item.label+'</option>'
                            }
                        })
                        return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="formType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                    options+
                               '</select>'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'itemType',
                    title: 'java类型*',
                    formatter:function(value, row, index){
                        let options ='';
                        pageFormObject.itemTypes.forEach(item=>{
                            if(item.value==value){
                                options +='<option selected value="'+item.value+'">'+item.label+'</option>'
                            }else{
                                options +='<option  value="'+item.value+'">'+item.label+'</option>'
                            }
                        })
                        return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="itemType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                    options+
                               '</select>'
                    }
                },
                {
                    width:100,
                    field: 'sqlType',
                    title: 'sql类型*',
                    formatter:function(value, row, index){
                        let options ='';
                        pageFormObject.sqlTypes.forEach(item=>{
                            if(item.value==value){
                                options +='<option selected value="'+item.value+'">'+item.label+'</option>'
                            }else{
                                options +='<option  value="'+item.value+'">'+item.label+'</option>'
                               }
                            })
                        return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="sqlType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                  options+
                               '</select>'
                    }
                },
                {
                    //取决于type
                    field: 'sqlLength',
                    title: 'sql字段长度',
                    width:150,
                    formatter: function (value, row, index) {
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="' + value + '" class="form-control" name="sqlLength" placeholder="字段长度">'
                    }
                },
                {
                    width:100,
                    field: 'queryType',
                    title: '查询组件*',
                    formatter:function(value, row, index){
                        let options ='';
                        pageFormObject.queryTypes.forEach(item=>{
                             if(item.value==value){
                                options +='<option selected value="'+item.value+'">'+item.label+'</option>'
                             }else{
                                options +='<option  value="'+item.value+'">'+item.label+'</option>'
                             }
                        })
                       return '<select onchange="pageFormObject.changeData('+ index +', this);"    data-width="100"   name="queryType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                  options+
                              '</select>'
                    }
                },
                {
                    width:100,
                    field: 'queryExp',
                    title: '查询表达式',
                    formatter:function(value, row, index){
                        //显隐取决于type
                        let options ='';
                        pageFormObject.queryExps.forEach(item=>{
                            if(item.value==value){
                                  options +='<option selected value="'+item.value+'">'+item.label+'</option>'
                            }else{
                                 options +='<option  value="'+item.value+'">'+item.label+'</option>'
                            }
                        })
                    return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="queryExp" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                 options+
                           '</select>'
                    }

                },
                {
                    //输入 0为不显示
                    field: 'listLength',
                    title: '列宽*',
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
                        let checked='';
                        if(value==1){
                            checked='checked'
                        }
                        return '<div class="custom-control custom-switch">' +
                            '       <input '+checked+' id="isMust'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" name="isMust" >' +
                            '       <label class="custom-control-label" for="isMust'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'isUnique',
                    title: '唯一',
                    formatter:function(value, row, index){
                        let checked='';
                        if(value==1){
                          checked='checked'
                         }
                        return '<div class="custom-control custom-switch">' +
                            '       <input '+checked+' id="isUnique'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" name="isUnique" >' +
                            '       <label class="custom-control-label" for="isUnique'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'isSort',
                    title: '排序',
                    formatter:function(value, row, index){
                        let checked='';
                        if(value==1){
                             checked='checked'
                          }
                        return '<div  class="custom-control custom-switch">' +
                            '        <input '+checked+' id="isSort'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" name="isSort" >' +
                            '       <label class="custom-control-label" for="isSort'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //取决于form
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
    changeSwitchData(index,obj){
        if($(obj).is(':checked')){
            $(obj).val(1)
        }else{
            $(obj).val(0)
        }
        this.changeData(index,obj);
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