<@
for(item in items){
@>
<div  class="custom-control custom-checkbox  custom-control-inline">
    <input class="custom-control-input" name="${name!}" id="${id}${itemLP.index}" type="checkbox" ${required!true==true?'':'required="required"'}"
           value="${item[itemValue]}"
    <@
    var all_value = ",";
    var item_value = "," + item[itemValue] + ",";
    if(isNotEmpty(values)){
    var t = type.name(values);
    if("String"==t){
    values = strutil.split(values,",");
    }
    for(d in values){
    all_value = all_value + d +",";
    };
    }
    @>
    <@ if(strutil.contain(all_value, item_value) == true){ @>
    checked
    <@ } @>
    />
    <label class="custom-control-label" for="${id}${itemLP.index}">${item[itemLabel]}</label>
</div>
<@ } @>

