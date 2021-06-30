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
        {value:8,label:"数字",filterItemType:['Integer','BigDecimal','Double','Float','Long'],filterQueryType:[0,6,7],dictable:false},
        {value:9,label:"部门选择器",filterItemType:['UpmsDept'],filterQueryType:[0],dictable:false},
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
        {value:'UpmsDept',label:"UpmsDept",filterSqlType: ['自定义']},
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
        {value:'datetime',label:"datetime",defaultLength: ''},
        {value:'tinytext',label:"tinytext",defaultLength: ''},
        {value:'text',label:"text",defaultLength: ''},
        {value:'longtext',label:"longtext",defaultLength: ''},
        {value:'自定义',label:"自定义",defaultLength: ''},
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
        {value:-1,label:"不查询"},
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
        },null,{error:true})

        <@ if(mode=='view'){ @>
        $('#gencodeForm').find('input,textarea,select').prop('disabled',true);
        $('#submit').prop('disabled',true);
        <@ } @>

        $("#templateType").on('change',function(){
            if($("#templateType").val()==2){
                $(".related-tree").show()
            }else{
                $(".related-tree").hide()
            }
        })
        $("#templateType").change()

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
                        return '<input  required="required" col-name="itemName" onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="items['+index+'].itemName" placeholder="字段名">'
                    }
                }
                , {
                    //输入
                    field: 'itemDesc',
                    title: '字段描述*',
                    width:150,
                    formatter:function(value, row, index){
                        return '<input  required="required" col-name="itemDesc" onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="items['+index+'].itemDesc" placeholder="字段描述">'
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
                        return '<select col-name="formType" onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="items['+index+'].formType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
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
                        let options='';
                        var formTypeFilter = pageFormObject.formTypes.filter(item=>{
                            return row["formType"] == item.value;
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
                        return '<select col-name="itemType" onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="items['+index+'].itemType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                    options+
                               '</select>'
                    }
                },
                {
                    width:100,
                    field: 'sqlType',
                    title: 'sql类型*',
                    formatter:function(value, row, index){
                        let itemTypeFilter = pageFormObject.itemTypes.filter(item=>{
                            return row["itemType"] == item.value;
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
                        return '<select col-name="sqlType" onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="items['+index+'].sqlType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
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

                        return '<input col-name="sqlLength" onchange="pageFormObject.changeData('+ index +', this);"  type="text"  value="' + value + '" class="form-control" name="items['+index+'].sqlLength" placeholder="字段长度">'
                        //如果 length与 当前sqltype default相等 则不变
                    }
                },
                {
                    width:100,
                    field: 'queryType',
                    title: '查询组件*',
                    formatter:function(value, row, index){
                        let options='';
                        var formTypeFilter = pageFormObject.formTypes.filter(item=>{
                            return row["formType"] == item.value;
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
                       return '<select col-name="queryType" onchange="pageFormObject.changeData('+ index +', this);"    data-width="100"   name="items['+index+'].queryType" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
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
                        var queryTypeFilter = pageFormObject.queryTypes.filter(item=>{
                            return row["queryType"] == item.value;
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
                    return '<select col-name="queryExp" onchange="pageFormObject.changeData('+ index +', this);"   data-width="100"   name="items['+index+'].queryExp" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                                 options+
                           '</select>'
                    }

                },
                {
                    //输入 0为不显示
                    field: 'listLength',
                    title: '列宽*(0为隐藏)',
                    width:100,
                    formatter:function(value, row, index){
                        return '<input  required="required" col-name="listLength" onblur="pageFormObject.changeData('+ index +', this);"  type="text"  value="'+value+'" class="form-control" name="items['+index+'].listLength" placeholder="列宽">'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'must',
                    title: '必填',
                    formatter:function(value, row, index){
                        let checked='';
                        if(value==1){
                            checked='checked'
                        }
                        return '<div class="custom-control custom-switch">' +
                            '       <input value="'+value+'"  type="hidden" name="items['+index+'].must" >' +
                            '       <input '+checked+' col-name="must"   id="must'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" >' +
                            '       <label class="custom-control-label" for="must'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'unrepeat',
                    title: '唯一',
                    formatter:function(value, row, index){
                        let checked='';
                        if(value==1){
                          checked='checked'
                        }
                        return '<div class="custom-control custom-switch">' +
                            '       <input value="'+value+'"  type="hidden" name="items['+index+'].unrepeat" >' +
                            '       <input '+checked+' col-name="unrepeat"  id="unrepeat'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" >' +
                            '       <label class="custom-control-label" for="unrepeat'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //toggle
                    width:100,
                    field: 'sort',
                    title: '排序',
                    formatter:function(value, row, index){
                        let checked='';
                        if(value==1) {
                            checked = 'checked'
                        }
                        return '<div  class="custom-control custom-switch">' +
                            '       <input value="'+value+'"  type="hidden" name="items['+index+'].sort" >' +
                            '        <input  col-name="sort" '+checked+' id="sort'+index+'" onclick="pageFormObject.changeSwitchData('+ index +', this);"  type="checkbox" class="custom-control-input" >' +
                            '       <label class="custom-control-label" for="sort'+index+'"></label>' +
                            '   </div>'
                    }
                },
                {
                    //取决于form
                    width:150,
                    field: 'dictCode',
                    title: '关联字典',
                    formatter:function(value, row, index){
                        var formTypeFilter = pageFormObject.formTypes.filter(item=>{
                            return row["formType"] == item.value;
                        })
                        let options = '';
                        pageFormObject.dictList.forEach(item=>{
                            options+="<option value="+item.code+" label="+item.name+">"+item.name+"</option> "
                        })
                        if(!formTypeFilter[0].dictable){
                            options='';
                        }

                        return '<select col-name="dictCode" onchange="pageFormObject.changeData('+ index +', this);"   data-width="150"   name="items['+index+'].dictCode" class="selectpicker item-select" data-style="btn-default" data-live-search="true">'+
                            options+
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
        <@ if(mode=='add'){ @>
            this.appendNewData()
        <@ }else{ @>
            var items= [];
            <@ for(gencodeItem in gencodeEntity.items){ @>
            items.push({
                'itemName':'${gencodeItem.itemName}',
                'itemDesc':'${gencodeItem.itemDesc}',
                'formType':${gencodeItem.formType},
                'itemType':'${gencodeItem.itemType}',
                'sqlType':'${gencodeItem.sqlType}',
                'sqlLength':'${gencodeItem.sqlLength}',
                'queryType':${gencodeItem.queryType},
                'queryExp':${gencodeItem.queryExp},
                'listLength':${gencodeItem.listLength},
                'must':${gencodeItem.must==true?1:0},
                'unrepeat':${gencodeItem.unrepeat==true?1:0},
                'sort':${gencodeItem.sort==true?1:0},
                'dictCode':'${gencodeItem.dictCode}',
            })
            <@ } @>
            this.appendData(items)
        <@ } @>
    },
    appendData(newData){
        this.tableRef.bootstrapTable('append', newData);
        $(".item-select").selectpicker('show')
    },
    appendNewData(){
        var newData = [{'itemName':'','itemDesc':'','formType':0,'itemType':'String','sqlType':'varchar','sqlLength':'255','queryType':0,'queryExp':0,'listLength':0,'must':0,'unrepeat':0,'sort':0,'dictCode':''}];
        this.appendData(newData);
    },
    //保存临时数据
    changeData(index,obj){
        var value = $(obj).val();
        var colName = $(obj).attr('col-name');
        //收集row
        var row = this.tableRef.bootstrapTable('getData')[index];
        row[colName] = value;

        //这里由于formatter实时更新有问题
        //为什么这里要两遍，第一遍是由于bootstrapTable会在updateRow时更新formatter 所以这里第一次是取UI改变后的值
        this.cascadeChange(colName,row);
        this.tableRef.bootstrapTable('updateRow',{index: index, row: row});
        //在UI改变后根据dom取最新元素是最准确的
        $(".item-select").selectpicker('show')
    },
    //级联改变其他值
    cascadeChange(colName,row){
        if(colName=='formType'){
            let formTypeFilter = pageFormObject.formTypes.filter(item=>{
                return row["formType"] == item.value;
            })
            row["itemType"] = formTypeFilter[0].filterItemType[0]
            row["queryType"] = formTypeFilter[0].filterQueryType[0]
            if(formTypeFilter[0].dictable){
                row["dictCode"]=this.dictList[0].code;
            }
            this.cascadeChange('itemType',row);
            this.cascadeChange('queryType',row);

        }else if(colName=='itemType'){
            let itemTypeFilter = pageFormObject.itemTypes.filter(item=>{
                return row["itemType"] == item.value;
            })
            row["sqlType"]= itemTypeFilter[0].filterSqlType[0]
            this.cascadeChange('sqlType',row);
        }else if(colName=='sqlType'){
            let sqlTypeFilter = pageFormObject.sqlTypes.filter(item=>{
                return row["sqlType"] == item.value;
            })
            row["sqlLength"]= sqlTypeFilter[0].defaultLength;
        }else if(colName=='queryType'){
            let queryTypeFilter = pageFormObject.queryTypes.filter(item=>{
               return row["queryType"] == item.value;
            })
            row["queryExp"] = queryTypeFilter[0].filterQueryExp[0]
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