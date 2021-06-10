<script>
var pageFormObject= {
    tableRef: null,
    dictList:[
    <@ for(dictItem in dictList){ @>
    {
    name:'${dictItem.name}',
    code:'${dictItem.code}'
    },
    <@ } @>

    ],
    formTypes:[
        {value:0,label:"不显示",filterItemType:['String','Integer','BigDecimal','boolean','Long','Date','Double','Float'],filterQueryType:[0,1],dictable:false},
        {value:1,label:"文本框",filterItemType:['String'],filterQueryType:[0,1],dictable:false},
        {value:2,label:"文本域",filterItemType:['String'],filterQueryType:[0,1],dictable:false},
        {value:3,label:"富文本",filterItemType:['String'],filterQueryType:[0],dictable:false},
        {value:4,label:"下拉",filterItemType:['String','Integer','boolean'],filterQueryType:[0,1,2,3,4],dictable:true},
        {value:5,label:"单选",filterItemType:['String','Integer'],filterQueryType:[0,1,2,3,4],dictable:true},
        {value:6,label:"复选",filterItemType:['String','Integer'],filterQueryType:[0,1,2,3,4],dictable:true},
        {value:7,label:"日期",filterItemType:['Date'],filterQueryType:[0,5],dictable:false},
        {value:8,label:"数字",filterItemType:['Double','Float','Long'],filterQueryType:[0,6,7],dictable:false},
        // {/*{value:9,label:"开关"}*/}
    ],
    //取决于form
    itemTypes:[
        {value:'String',label:"String",filterSqlType:['varchar','tinytext','text','longtext']},
        {value:'Integer',label:"Integer",filterSqlType: ['int','tinyint']},
        {value:'BigDecimal',label:"BigDecimal",filterSqlType: ['decimal']},
        {value:'boolean',label:"boolean",filterSqlType: ['bit','char']},
        {value:'Long',label:"Long",filterSqlType: ['bigint']},
        {value:'Date',label:"Date",filterSqlType: ['datetime']},
        {value:'Double',label:"Double",filterSqlType: ['double']},
        {value:'Float',label:"Float",filterSqlType: ['float']},
    ],
    //取决于itemTypes
    sqlTypes:[
        {value:'varchar',label:"varchar",defaultLength:'255'},
        {value:'int',label:"int",defaultLength: '11'},
        {value:'tinyint',label:"tinyint",defaultLength: '4'},
        {value:'bit',label:"bit",defaultLength: '1'},
        {value:'bigint',label:"bigint",defaultLength: '20'},
        {value:'double',label:"double",defaultLength: '10,2'},
        {value:'float',label:"float",defaultLength: '10,2'},
        {value:'decimal',label:"decimal",defaultLength: '10,2'},
        {value:'char',label:"char",defaultLength:'1'},
        {value:'datetime',label:"datetime",defaultLength: '0'},
        {value:'tinytext',label:"tinytext",defaultLength: '0'},
        {value:'text',label:"text",defaultLength: '0'},
        {value:'longtext',label:"longtext",defaultLength: '0'},
    ],
    //取决于form
    queryTypes:[
        {value:0,label:"不查询",filterQueryExp:[-1]},
        {value:1,label:"文本框",filterQueryExp:[0,1,2,3]},
        {value:2,label:"下拉框",filterQueryExp:[0]},
        {value:3,label:"单选框",filterQueryExp:[0]},
        {value:4,label:"复选框",filterQueryExp:[0]},
        {value:5,label:"日期",filterQueryExp:[8]},
        {value:6,label:"数字",filterQueryExp:[0,1,4,5,6,7]},
        {value:7,label:"数字范围",filterQueryExp:[8]},
    ],
    //取决于queryTypes
    queryExps:[
        {value:0,label:"相等"},
        {value:1,label:"相似"},
        {value:2,label:"左相似"},
        {value:3,label:"右相似"},
        {value:4,label:"大于"},
        {value:5,label:"小于"},
        {value:6,label:"大于等于"},
        {value:7,label:"小于等于"},
        {value:8,label:"之间"},

    ],
    startAllLisener:function(){
        $form.submit("#entityForm",function(data){
            if (data.success) {
                go_into("${ctx}/scrum/gencode/entity");
            }
        },function(data){
            //todo 处理数据
            console.log(data);
        },{error:true})
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
                    field: 'items.itemName',
                    title: '字段名*',
                    width:150,
                    formatter:function(value, row, index){
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="items.itemName" placeholder="字段名">'
                    }
                }
                , {
                    //输入
                    field: 'items.itemDesc',
                    title: '字段描述*',
                    width:150,
                    formatter:function(value, row, index){
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="items.itemDesc" placeholder="字段描述">'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'items.formType',
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
                        return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="items.formType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                    options+
                               '</select>'
                    }
                },
                {
                    //下拉
                    width:100,
                    field: 'items.itemType',
                    title: 'java类型*',
                    formatter:function(value, row, index){
                        let options='';
                        var formTypeFilter = pageFormObject.formTypes.filter(item=>{
                            return row["items.formType"] == item.value;
                        })
                        pageFormObject.itemTypes.filter(item=>{
                            return ( formTypeFilter[0].filterItemType.indexOf(item.value) != -1)
                        }).forEach(item=>{
                            if(item.value==value){
                                options +='<option selected value="'+item.value+'">'+item.label+'</option>'
                            }else{
                                options +='<option  value="'+item.value+'">'+item.label+'</option>'
                            }
                        })
                        return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="items.itemType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                    options+
                               '</select>'
                    }
                },
                {
                    width:100,
                    field: 'items.sqlType',
                    title: 'sql类型*',
                    formatter:function(value, row, index){
                        let itemTypeFilter = pageFormObject.itemTypes.filter(item=>{
                            return row["items.itemType"] == item.value;
                        })
                        let options ='';
                        pageFormObject.sqlTypes.filter(item=>{
                            return (itemTypeFilter[0].filterSqlType.indexOf(item.value) != -1)
                        }).forEach(item=>{
                            if(item.value==value){
                                options +='<option  selected value="'+item.value+'">'+item.label+'</option>'
                            }else{
                                options +='<option  value="'+item.value+'">'+item.label+'</option>'
                               }
                            })
                        return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="items.sqlType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                  options+
                               '</select>'
                    }
                },
                {
                    //取决于type
                    field: 'items.sqlLength',
                    title: 'sql字段长度',
                    width:150,
                    formatter: function (value, row, index) {

                        return '<input onchange="pageFormObject.changeData('+ index +', this);"  type="text"  value="' + value + '" class="form-control" name="items.sqlLength" placeholder="字段长度">'
                        //如果 length与 当前sqltype default相等 则不变
                    }
                },
                {
                    width:100,
                    field: 'items.queryType',
                    title: '查询组件*',
                    formatter:function(value, row, index){
                        let options='';
                        var formTypeFilter = pageFormObject.formTypes.filter(item=>{
                            return row["items.formType"] == item.value;
                        })
                        pageFormObject.queryTypes.filter(item=>{
                            return ( formTypeFilter[0].filterQueryType.indexOf(item.value) != -1)
                        }).forEach(item=>{
                             if(item.value==value){
                                options +='<option selected value="'+item.value+'">'+item.label+'</option>'
                             }else{
                                options +='<option  value="'+item.value+'">'+item.label+'</option>'
                             }
                        })
                       return '<select onchange="pageFormObject.changeData('+ index +', this);"    data-width="100"   name="items.queryType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                  options+
                              '</select>'
                    }
                },
                {
                    width:100,
                    field: 'items.queryExp',
                    title: '查询表达式',
                    formatter:function(value, row, index){
                        //显隐取决于type
                        let options ='';
                        var queryTypeFilter = pageFormObject.queryTypes.filter(item=>{
                            return row["items.queryType"] == item.value;
                        })
                        pageFormObject.queryExps.filter(item=>{
                            return ( queryTypeFilter[0].filterQueryExp.indexOf(item.value) != -1)
                        }).forEach(item=>{
                            if(item.value==value){
                                  options +='<option selected value="'+item.value+'">'+item.label+'</option>'
                            }else{
                                 options +='<option  value="'+item.value+'">'+item.label+'</option>'
                            }
                        })
                    return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="items.queryExp" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                 options+
                           '</select>'
                    }

                },
                {
                    //输入 0为不显示
                    field: 'items.listLength',
                    title: '列宽*(0为隐藏)',
                    width:100,
                    formatter:function(value, row, index){
                        return '<input onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="items.listLength" placeholder="列宽">'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'items.isMust',
                    title: '必填',
                    formatter:function(value, row, index){
                        let checked='';
                        if(value==1){
                            checked='checked'
                        }
                        return '<div class="custom-control custom-switch">' +
                            '       <input '+checked+' id="isMust'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" name="items.isMust" >' +
                            '       <label class="custom-control-label" for="isMust'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'items.isUnique',
                    title: '唯一',
                    formatter:function(value, row, index){
                        let checked='';
                        if(value==1){
                          checked='checked'
                         }
                        return '<div class="custom-control custom-switch">' +
                            '       <input '+checked+' id="isUnique'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" name="items.isUnique" >' +
                            '       <label class="custom-control-label" for="isUnique'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'items.isSort',
                    title: '排序',
                    formatter:function(value, row, index){
                        let checked='';
                        if(value==1){
                             checked='checked'
                          }
                        return '<div  class="custom-control custom-switch">' +
                            '        <input '+checked+' id="isSort'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" name="items.isSort" >' +
                            '       <label class="custom-control-label" for="isSort'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //取决于form
                    width:150,
                    field: 'items.dictCode',
                    title: '关联字典',
                    formatter:function(value, row, index){
                        var formTypeFilter = pageFormObject.formTypes.filter(item=>{
                            return row["items.formType"] == item.value;
                        })
                        let options = '';
                        pageFormObject.dictList.forEach(item=>{
                            options+="<option value="+item.code+" label="+item.name+">"+item.name+"</option> "
                        })
                        if(formTypeFilter[0].dictable){
                            return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="150"   name="items.dictCode" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                options+
                            '</select>'
                        }else{
                            return '<select onchange="pageFormObject.changeData('+ index +', this);"   data-width="150"   name="items.dictCode" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                            '</select>'
                        }
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
        this.tableRef.bootstrapTable('append', [{'items.itemName':'','items.itemDesc':'','items.formType':0,'items.itemType':'String','items.sqlType':'varchar','items.sqlLength':'255','items.queryType':0,'items.queryExp':0,'items.listLength':0,'items.isMust':0,'items.isUnique':0,'items.isSort':0,'items.dictCode':''}]);
        $(".item-select").selectpicker('show')
    },
    //保存临时数据
    changeData(index,obj){
        var value = $(obj).val();
        var name = $(obj).attr('name');
        //收集row
        var row = this.tableRef.bootstrapTable('getData')[index];
        row[name] = value;

        //这里由于formatter实时更新有问题
        //为什么这里要两遍，第一遍是由于bootstrapTable会在updateRow时更新formatter 所以这里第一次是取UI改变后的值
        this.cascadeChange(name,row);
        this.tableRef.bootstrapTable('updateRow',{index: index, row: row});
        //在UI改变后根据dom取最新元素是最准确的
        $(".item-select").selectpicker('show')
    },
    //级联改变其他值
    cascadeChange(name,row){
        if(name=='items.formType'){
            let formTypeFilter = pageFormObject.formTypes.filter(item=>{
                return row["items.formType"] == item.value;
            })
            row["items.itemType"] = formTypeFilter[0].filterItemType[0]
            row["items.queryType"] = formTypeFilter[0].filterQueryType[0]
            if(formTypeFilter[0].dictable){
                row["items.dictCode"]=this.dictList[0].code;
            }
            this.cascadeChange('items.itemType',row);
            this.cascadeChange('items.queryType',row);

        }else if(name=='items.itemType'){
            let itemTypeFilter = pageFormObject.itemTypes.filter(item=>{
                return row["items.itemType"] == item.value;
            })
            row["items.sqlType"]= itemTypeFilter[0].filterSqlType[0]
            this.cascadeChange('items.sqlType',row);
        }else if(name=='items.sqlType'){
            let sqlTypeFilter = pageFormObject.sqlTypes.filter(item=>{
                return row["items.sqlType"] == item.value;
            })
            row["items.sqlLength"]= sqlTypeFilter[0].defaultLength;
        }else if(name=='items.queryType'){
            let queryTypeFilter = pageFormObject.queryTypes.filter(item=>{
               return row["items.queryType"] == item.value;
            })
            row["items.queryExp"] = queryTypeFilter[0].filterQueryExp[0]
        }
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