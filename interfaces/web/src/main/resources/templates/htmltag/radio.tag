<@
for(item in items){
@>
    <div class="custom-control custom-radio custom-control-inline" >
        <input ${required!true==true?'':'required="required"'} name="${path}" id="${path}${itemLP.index}" ${item.value == value! ?"checked":""} type="radio"  value="${item.value}" class="custom-control-input">
        <label  class="custom-control-label" for="${path}${itemLP.index}">${item.name}</label>
    </div>
<@ } @>

