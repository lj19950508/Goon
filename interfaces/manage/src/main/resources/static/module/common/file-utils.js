var $file={
    getTemplateByUrl:function(url){
        let fileType = url.split('.')[1];
        let index = url.lastIndexOf("/")
        let name = url.substring(index+1,url.length);
        if(fileType=='jpg'|| fileType=='jpeg'||fileType=="png"||fileType=="bmp"||fileType=="gif"){
            return '<a  target="_blank" href="'+url+'" ><img style="width: 50px;height: 50px" class="img-thumbnail" src="'+url+'" alt="'+name+'"></a>'
        }else{
            console.log(123)
            return '<a target="_blank" href="'+url+'">'+name+'</a></br>'
        }
    }
}
