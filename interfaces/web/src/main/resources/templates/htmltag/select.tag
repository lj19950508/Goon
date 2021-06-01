<select  id="${path}" name="${path}" readonly="${readonly!false}" class="selectpicker" data-style="btn-default" data-live-search="true">
    <@ if(notAllowNull!false == false){ @>
    <option value="" label=""></option>
    <@ } @>
    <@
        for(item in items){
    @>
    <option value="${item[itemValue]}" ${item[itemValue] == value!""?"selected":""} label="${item[itemLabel]}"  >${item[itemLabel]}</option>

    <@ } @>
</select>



