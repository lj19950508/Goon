<textarea class="form-control" id="${id}"  name="${name}" rows="${rows!}" maxlength="${maxlength!}" onkeyup="checkMaxInput(this)">${value!}</textarea>
<script>
    //多行文本输入框剩余字数计算
    function checkMaxInput(obj) {
        var $obj = $(obj);
        var maxLen = obj.getAttribute("maxlength");
        var objVal = obj.value.replace(/\r(?!\n)|\n(?!\r)/g, '\r\n');
        if (objVal.length > maxLen) {
            objVal = objVal.substring(0, maxLen);
            obj.value = objVal;
        }
        if ($obj.next(".maxinput").length == 0) {
            $obj.after('<div class="maxinput" style="text-align:right;">' + objVal.length + '/' + maxLen + '</div>');
        } else {
            $obj.next(".maxinput").text((obj.value.replace(/\r(?!\n)|\n(?!\r)/g, '\r\n')).length + '/' + maxLen);
        }
    }
    $(document).ready(function() {
        checkMaxInput(document.getElementById("${id}"));
    });
</script>