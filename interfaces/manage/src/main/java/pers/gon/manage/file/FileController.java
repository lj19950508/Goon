package pers.gon.manage.file;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.gon.infrastructure.common.config.global.GlobalProperties;
import pers.gon.infrastructure.common.entity.CommonResult;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/file")
public class FileController {
    @Value("${file.path}")
    String storegePath;

    @Autowired
    HttpServletRequest request;

    @Autowired
    GlobalProperties globalProperties;

    //for bootstrap-fileinput
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/upload")
    public CommonResult upload(MultipartFile uploadFile, String uploadPath) {
        if(uploadFile==null ){
            return CommonResult.fail("上传文件识别失败");
        }
        if(StringUtils.isEmpty(uploadPath)){
            return CommonResult.fail("请输入上传路径");
        }
        Date now =new Date();
        int month = DateUtil.month(now);
        int year = DateUtil.year(now);
        int day = DateUtil.dayOfMonth(now);
        String filename = uploadFile.getOriginalFilename();
        String realtivePath = uploadPath+"/"+year+"/"+month+"/"+day+"/";
        String filepath = storegePath+realtivePath;

        //如果文件名字重复自动重命名
        while (FileUtil.exist(filepath+filename)){
            filename =   FileUtil.getPrefix(filename)+" - 副本"+"."+FileUtil.getSuffix(filename);
        }
        File saveFile = FileUtil.touch(filepath+filename);

        uploadFile.transferTo(saveFile);
        String fileNetworkUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+"files/";
        BootstrapFileInputResult bootstrapFileInputResult = new BootstrapFileInputResult();
        bootstrapFileInputResult.setCaption(filename);
        bootstrapFileInputResult.setDownloadUrl(fileNetworkUrl+realtivePath+filename);
        bootstrapFileInputResult.setSize(uploadFile.getSize());
        bootstrapFileInputResult.setKey(fileNetworkUrl+realtivePath+filename);
        bootstrapFileInputResult.setUrl(request.getContextPath()+"/"+globalProperties.getAdminPath()+"/file/delete?path="+realtivePath+filename);
        bootstrapFileInputResult.setFiletype(new MimetypesFileTypeMap().getContentType(filename));
        return CommonResult.ok(fileNetworkUrl+realtivePath+filename)
                .add("initialPreview", ListUtil.toList(fileNetworkUrl+realtivePath+filename))
                .add("initialPreviewConfig",ListUtil.toList(bootstrapFileInputResult));
    }

    @SneakyThrows
    @ResponseBody
    @RequestMapping("/info")
    public CommonResult info(String urls) {
        List<BootstrapFileInputResult> configList = new ArrayList<>();
        String[] urlArray = urls.split(",");
        for(int i = 0 ; i < urlArray.length ; i++){
            String fileNetworkUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+"files/";
            String relativeFile = urls.replaceAll(fileNetworkUrl,"");
            String localFile = storegePath+relativeFile;
            File file = FileUtil.file(localFile);
            BootstrapFileInputResult bootstrapFileInputResult = new BootstrapFileInputResult();
            bootstrapFileInputResult.setSize(file.length());
            bootstrapFileInputResult.setCaption(file.getName());
            bootstrapFileInputResult.setDownloadUrl(urls);
            bootstrapFileInputResult.setKey(urls);
            bootstrapFileInputResult.setUrl(request.getContextPath()+"/"+globalProperties.getAdminPath()+"/file/delete?path="+relativeFile);
            bootstrapFileInputResult.setFiletype(new MimetypesFileTypeMap().getContentType(urls));
            configList.add(bootstrapFileInputResult);
        }
        return CommonResult.ok()
                .add("initialPreview", ListUtil.toList(urlArray))
                .add("initialPreviewConfig",configList);
    }

    //多提供一个接口 根据URl信息返回

    @SneakyThrows
    @ResponseBody
    @RequestMapping("/delete")
    public CommonResult delete(String path) {
        FileUtil.del(storegePath+path);
        return CommonResult.ok();
    }
}
