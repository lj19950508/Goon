${r'<@'}layout("/include/_container.html", {title:"${entityName}", parent:"${moduleName}"}){  ${r'@>'}
<div class="row ">

<#--  todo----------------------  加入相应分类形选择-->
    <div class="col-sm-6 ">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">部门选择</h5>
                <div class="white-box">
                    <!-- 搜索 -->
                    <div class="input-group pt-2 mb-3 pl-2 pr-2">
                        <input id="search-q" type="text" class="form-control"   placeholder="查找..." >
                        <div class="input-group-append">
                            <button class="btn btn-primary" type="button" ><i class="fa fa-search"></i></button>
                        </div>
                    </div>
                    <ul id="deptTree" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>


    <div class="col-sm-24 mb-2 collapse" id="search-collapse">
        <div class="card">

            <div class="card-body">
                <a class="close" onclick="$('#search-collapse').slideToggle()">
                    <i class="fa fa-close"></i>
                </a>
                <form id="searchForm">
                    <div class="form-row align-items-center">
                        <#list items as item>
                        <#if item.queryType!=0>
                        <#if item.queryType==1>
                            <div class="col-auto">
                                <div class="input-group mb-2">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">${item["itemDesc"]}</div>
                                    </div>
                                    <input type="text" class="form-control" name="${item["itemName"]}" placeholder="输入${item["itemDesc"]}">
                                </div>
                            </div>
                        </#if>
                        <#if item.queryType==2>
                            <div class="col-auto">
                                <div class="input-group mb-2">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">${item["itemDesc"]}</div>
                                    </div>
                                    ${r'<#'}select id="${item["itemName"]}" name="${item["itemName"]}" items="${r'${'}dict.get('${item["dictCode"]}')}" itemLabel="name" itemValue="value" />
                                </div>
                            </div>

                        </#if>
                        <#if item.queryType==3>
                            <div class="col-auto">
                                <div class="input-group mb-2">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">${item["itemDesc"]}</div>
                                    </div>
                                    <div class="query-container">
                                    ${r'<#'}radio id="${item["itemName"]}" name="${item["itemName"]}"  items="${r'${'}dict.get('${item["dictCode"]}')}" />
                                    </div>
                                </div>
                            </div>
                        </#if>
                        <#if item.queryType==4>
                            <div class="col-auto">
                                <div class="input-group mb-2">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">${item["itemDesc"]}</div>
                                    </div>
                                    <div class="query-container">
                                    <#if item.formType!=6>
                                        ${r'<#'}checkbox id="${item["itemName"]}s" name="${item["itemName"]}s"  items="${r'${'}dict.get('${item["dictCode"]}')}" itemLabel="name" itemValue="value"    />
                                    </#if>
                                    <#if item.formType==6>
                                        ${r'<#'}bracketCheckbox id="${item["itemName"]}" name="${item["itemName"]}"  items="${r'${'}dict.get('${item["dictCode"]}')}" itemLabel="name" itemValue="value"    />
                                    </#if>
                                    </div>
                                </div>
                            </div>
                        </#if>
                        <#if item.queryType==5  >
                            <div class="col-auto">
                                <div class="input-group mb-2">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">${item["itemDesc"]}</div>
                                    </div>
                                    ${r'<#'}dateregionpicker id="${item["itemName"]}" name="${item["itemName"]}"  />
                                </div>
                            </div>
                        </#if>
                        <#if item.queryType==6 && item.queryExp!=8 >
                            <div class="col-auto">
                                <div class="input-group mb-2">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">${item["itemDesc"]}</div>
                                    </div>
                                    <input type="number" class="form-control" name="${item["itemName"]}" placeholder="输入${item["itemDesc"]}">
                                </div>
                            </div>
                        </#if>
                        <#if item.queryType==6 && item.queryExp==8 >
                            <div class="col-auto">
                                <div class="input-group mb-2">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">${item["itemDesc"]}</div>
                                    </div>
                                    <input type="number" class="form-control" name="start${item["itemName"]?cap_first}" placeholder="最小${item["itemDesc"]}">&nbsp;~&nbsp;
                                    <input type="number" class="form-control" name="end${item["itemName"]?cap_first}" placeholder="最大${item["itemDesc"]}">
                                </div>
                            </div>
                        </#if>
                        </#if>
                        </#list>
                        <div class="col-auto">
                            <button id="search" type="button" class="btn btn-default mb-2">查询</button>
                            <button id="clear" type="button" class="btn btn-default mb-2">重置</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="col-sm-24 ">
        <div class="card">
            <div class="card-body">
                <div class="fixed-table-toolbar">
                    <div class="bs-bars float-left">
                        <div id="toolbar">
                            ${r'<@'} if(shiro.hasPermission("${moduleName?upper_case}:${entityName?upper_case}:ADD") ){ ${r'@>'}
                            <button id="add" class="btn btn-primary waves-effect w-xs waves-light" onclick="pageObject.add()"><i
                                    class="fa fa-plus"></i> 新建
                            </button>
                            ${r'<@ } @>'}
                            ${r'<@'} if(shiro.hasPermission("${moduleName?upper_case}:${entityName?upper_case}:EDIT") ){${r'@>'}
                            <button id="edit" class="btn btn-default waves-effect w-xs waves-light" disabled=""
                                    onclick="pageObject.edit(pageObject.getIdSelections())">
                                <i class="fa fa-edit"></i> 修改
                            </button>
                            ${r'<@ } @>'}
                            ${r'<@'} if(shiro.hasPermission("${moduleName?upper_case}:${entityName?upper_case}:DEL") ){ ${r'@>'}
                            <button id="remove" class="btn btn-outline-danger waves-effect w-xs waves-light" disabled=""
                                    onclick="pageObject.del(pageObject.getIdSelections())">
                                <i class="fa fa-remove"></i> 删除
                            </button>
                            ${r'<@ } @>'}
                            <#if excel==true>
                            ${r'<@'} if(shiro.orPermission("${moduleName?upper_case}:${entityName?upper_case}:VIEW", "${moduleName?upper_case}:${entityName?upper_case}:ADD", "${moduleName?upper_case}:${entityName?upper_case}:EDIT")){ ${r'@>'}
                            <div  class="dropdown inline-block">
                                <button type="button" class="btn btn-default waves-effect w-xs waves-light dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-file-excel-o"></i>  Excel
                                </button>
                                <div class="dropdown-menu">
                                    ${r'<@'} if(shiro.orPermission("${moduleName?upper_case}:${entityName?upper_case}:VIEW", "${moduleName?upper_case}:${entityName?upper_case}:ADD", "${moduleName?upper_case}:${entityName?upper_case}:EDIT")){ ${r'@>'}
                                    <a class="dropdown-item" onclick="pageObject.export(pageObject.getIdSelections())" >导出</a>
                                    ${r'<@'} } ${r'@>'}
                                    ${r'<@'} if(shiro.hasPermission("${moduleName?upper_case}:${entityName?upper_case}:ADD") ){ ${r'@>'}
                                        <a class="dropdown-item" onclick="pageObject.selectFile()">导入</a>
                                    ${r'<@'} } ${r'@>'}
                                    ${r'<@'} if(shiro.orPermission("${moduleName?upper_case}:${entityName?upper_case}:VIEW", "${moduleName?upper_case}:${entityName?upper_case}:ADD", "${moduleName?upper_case}:${entityName?upper_case}:EDIT")){ ${r'@>'}
                                        <a class="dropdown-item" onclick="pageObject.template()">模板</a>
                                    ${r'<@'} } ${r'@>'}
                                    <div class="d-none"><input  type="file" id="excelFile" onchange="pageObject.import()"></div>
                                </div>
                            </div>
                            ${r'<@ } @>'}
                            </#if>


                        </div>
                    </div>
                    <div class="columns columns-right btn-group float-right">
                        <button class="btn btn-default" type="button" name="showSearch" aria-label="Search" onclick="$('#search-collapse').slideToggle()"
                                title="Search">
                            <i class="fa fa-search"></i>
                        </button>
                        <button class="btn btn-default" type="button" name="refresh" aria-label="Refresh" title="刷新" onclick="pageObject.refresh()">
                            <i class="fa fa-refresh"></i>
                        </button>

                    </div>
                </div>
                <table id="${entityName}Table" class="table-bordered table-striped" data-toolbar="#toolbar"></table>
            </div>
        </div>
    </div>
</div>
${r'<@ } @>'}

${r'<@'}include("${entityName}.js"){}${r'@>'}