$.deepFind=function(obj,path){
    for (var i=0, path=path.split('.'), len=path.length; i<len; i++){
        if(obj[path[i]]){
            obj = obj[path[i]];
        }else{
            return null;
        }
    };
    return obj;
}

$.download=function(url,obj){
    let form = document.createElement("form");
    form.id= "tempDownloadForm";
    form.name = "tempDownloadForm";
    form.method = 'post';
    form.action=url;
    document.body.append(form);
    console.log(obj)
    for (let item in obj) {
        let nodeHtml = '';
        if(Array.isArray(obj[item])){
            for(let i in obj[item]){
                nodeHtml= '<input type="hidden" name="'+item+'[]" value="'+obj[item][i]+'"/>'
                form.appendChild($(nodeHtml)[0])
            }

        }else{
            nodeHtml = '<input type="hidden" name="'+item+'" value="'+obj[item]+'"/>'
            form.appendChild($(nodeHtml)[0])
        }
    }

    form.submit();
    document.body.removeChild(form);
}

