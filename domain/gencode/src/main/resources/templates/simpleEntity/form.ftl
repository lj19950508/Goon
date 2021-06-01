${r'<@'} layout("/include/_container.html", {title:"${entityName}", parent:"${moduleName}"}){ ${r'@>'}

<div class="row ">
    <div class="col-sm-24 ">
        <form id="${entityName}Form" action="${r'${ctx}'}/${moduleName}/${entityName}/save" method="post" novalidate>
            <div class="card">
                <div class="card-body">
                    <div class="col-24  pl-0 ">
                        <button type="button" onclick="javascript:location=document.referrer;" class="btn btn-default">
                            <i class="fa fa-arrow-left"></i> 返回
                        </button>
                        <button id="submit" type="button" class="btn btn-primary" data-loading-text="正在提交..."
                                onclick="$('#${entityName}Form').trigger('submit')">
                            <i class="fa fa-save"></i> 提交
                        </button>
                    </div>
                    <div class="col-24 mt-2 pl-0">
                        <input type="hidden" name="id" value="${r'${'}${entityName}.id!}"/>
                        <table class="table table-bordered">
                            <tbody>
                                <#list items as item>
                                <#if item.formType !=0>
                                <tr>
                                    <td class="active width-15 ">
                                        <span class="pull-right col-form-label">
                                            <#if item.must==true><span class="text-danger">*</#if></span>${item["itemName"]}：
                                        </span>
                                    </td>
                                    <td>
                                        <#if item.formType ==1>
                                        <input type="text" class="form-control" value="${r'${'}${entityName}.${item["itemName"]}!}" name="${item["itemName"]}" placeholder="输入${item["itemDesc"]}" <#if item.must==true>required="required"</#if>>
                                        </#if>
                                        <#if item.formType ==2>
                                        ${r'<#'}textarea id="text${item["itemName"]?cap_first}" name="${item["itemName"]}" maxlength="255" value="${r'${'}${entityName}.${item["itemName"]}!}"/>
                                        </#if>
                                        <#if item.formType ==3>
                                        ${r'<#'}editor name="${item["itemName"]}" id="${item["itemName"]}" value="${r'${'}${entityName}.${item["itemName"]}!}"/>
                                        </#if>
                                        <#if item.formType ==4>
                                        ${r'<#'}select path="${item["itemName"]}"  <#if item.must==true> notAllowNull="false"</#if> items="${r'${'}dict.get('${item["dictCode"]}')}"  value="${r'${'}${entityName}.${item["itemName"]}!}"  itemLabel="name" itemValue="value"/>
                                        </#if>
                                        <#if item.formType ==5>
                                        ${r'<#'}radio path="${item["itemName"]}" <#if item.must==true>required="required"</#if>  items="${r'${'}dict.get('${item["dictCode"]}')}" value="${r'${'}${entityName}.${item["itemName"]}!}"/>
                                        </#if>
                                        <#if item.formType ==6>
                                        ${r'<#'}simpleCheckbox path="${item["itemName"]}" <#if item.must==true>required="required"</#if> items="${r'${'}dict.get('${item["dictCode"]}')}" itemLabel="name" itemValue="value"  values="${r'${'}${entityName}.${item["itemName"]}!}"   />
                                        </#if>
                                        <#if item.formType ==7>
                                        ${r'<#'}datepicker path="${item["itemName"]}" <#if item.must==true>required="required"</#if> value="${r'${'}${entityName}.${item["itemName"]}!}"   />
                                        </#if>
                                        <#if item.formType ==8>
                                        <input type="number" class="form-control" value="${r'${'}${entityName}.${item["itemName"]}!}" name="${item["itemName"]}" placeholder="输入${item["itemDesc"]}" <#if item.must==true>required="required"</#if>>
                                        </#if>
                                        <#if item.must==true><div class="invalid-feedback">输入${item["itemDesc"]}!</div></#if>
                                    </td>
                                </tr>
                                </#if>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
${r'<@ } @>'}
${r'<@'} include("${entityName}Form.js"){} ${r'@>'}