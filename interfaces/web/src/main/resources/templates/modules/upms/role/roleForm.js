<script>
var pageFormObject={
    menuTree:null,
    setDataScope:function(roleId,menuCode,dsValue){
      $req.post("${ctx}/upms/role/setDatascopeValue",{roleId:roleId,menuCode:menuCode,dsValue:dsValue},function(res){

      },{success:true})
    },
    startAllLisener:function(){
        $form.submit("#roleForm",function(data){
            if (data.success) {
                go_into("${ctx}/upms/role");
            }
        },function(arr,form,options){
            let menuIds = pageFormObject.getCheckMenuId();
            for(let i = 0 ; i <menuIds.length ;i++){
                arr.push({name:"menus",value:menuIds[i]})
            }
        },{error:true})
        <@ if(mode=='view'){ @>
        $('#roleForm').find('input,textarea,select').prop('disabled',true);
        $('#submit').prop('disabled',true);
        <@ } @>
    },
    menuTreeInit(){
        let setting = {
            async:{
                enable:true,
                autoParam:['id'],
                dataType:'json',
                type:'get',
                url:"${ctx}/upms/menu/list",
                dataFilter:function(treeId,parentNode,responseData){
                    return responseData.data ;
                }
            },
            data:{
                key:{
                    url:"none"
                },
                simpleData: {
                    idKey: "id",
                    pIdKey:"parent.id",
                    enable:true,
                }
            },
            check:{enable:true,chkboxType:{ "Y": "p", "N": "s" }},
            callback:{
                onClick:function(event, treeId, treeNode,clickFlag){
                },
                onAsyncSuccess:function(event, treeId, treeNode, msg){
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    var nodes = treeObj.getNodes();
                    treeObj.expandNode(nodes[0], true, true, true);
                    pageFormObject.userMenuInit();

                },
                beforeRightClick: function(treeId,node){
                    //如果是修改才可以
                    <@ if(mode=='edit'){ @>
                    if(node!=null && node.type ==1){
                        $('.current-datascope').remove();
                        let  datascopeItems= '${dict.getJSON('upms_datascope_type')}';
                        let datascopeItemsObj = JSON.parse(datascopeItems);
                        let menuItems={
                            "current": {name: node.name, },
                            "sep1": "---------",
                        }
                        for(let i = 0 ; i < datascopeItemsObj.length ; i++){
                            menuItems[datascopeItemsObj[i].name]=datascopeItemsObj[i]
                        }
                        let menu = $.contextMenu({
                            selector: '#'+node.tId,
                            callback: function(key, options) {
                                _this = this;
                                for(let i in menuItems){
                                    if(key==i && null!=menuItems[i].value){
                                        pageFormObject.setDataScope("${upmsRole.id!}",node.code,menuItems[i].value)
                                    }
                                }
                            },
                            items: menuItems
                        });
                        $req.get("${ctx}/upms/role/datascopeName",{roleId:"${upmsRole.id!}",menuCode:node.code},function(res){
                            let current = res.data;
                            var ele = $(".context-menu-item span:contains("+current+")").map(function() {
                                if ($(this).text() == current) {
                                    return this;
                                }
                            });
                            ele.append("<span class='current-datascope'>✔</span>");
                        })

                    }
                    <@ } @>

                }
            },

        }
        this.menuTree = $.fn.zTree.init($('#menuTree'),setting,null);
    },
    userMenuInit:function(){
        <@if(mode=="view" || mode=="edit") { @>
            let selectMenuList = '${selectMenuList}';
            let nodes = this.menuTree.transformToArray(this.menuTree.getNodes());
            for (let i=0, l=nodes.length; i < l; i++) {
                if(selectMenuList.includes(nodes[i].id)){
                    this.menuTree.checkNode(nodes[i], true, true);
                }
            }
            this.getCheckMenuId()
        <@ } @>
    },
    getCheckMenuId(){
        let nodes =this.menuTree.getCheckedNodes()
        let menuIds = [];
        for(let i = 0 ; i < nodes.length ; i++){
            menuIds.push(nodes[i].id);
        }
        return menuIds;
    },
    init:function () {
        this.startAllLisener();
        this.menuTreeInit();
    }

};


$(function () {
    pageFormObject.init()
})


</script>