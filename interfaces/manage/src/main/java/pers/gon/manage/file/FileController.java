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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collections;
import java.util.Date;


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
    public CommonResult upload(MultipartFile file, String uploadPath) {
        if(file==null ){
            return CommonResult.fail("上传文件识别失败");
        }
        if(StringUtils.isEmpty(uploadPath)){
            return CommonResult.fail("请输入上传路径");
        }
        Date now =new Date();
        int month = DateUtil.month(now)+1;
        int year = DateUtil.year(now);
        int day = DateUtil.dayOfMonth(now);
        String filename = file.getOriginalFilename();
        String realtivePath = uploadPath+"/"+year+"/"+month+"/"+day+"/";
        String filepath = storegePath+realtivePath;

        //如果文件名字重复自动重命名
        while (FileUtil.exist(filepath+filename)){
            filename =   FileUtil.getPrefix(filename)+" - 副本"+"."+FileUtil.getSuffix(filename);
        }
        File saveFile = FileUtil.touch(filepath+filename);

        file.transferTo(saveFile);
        String fileNetworkUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+"files/";

        return CommonResult.ok(fileNetworkUrl+realtivePath+filename);
    }


    @SneakyThrows
    @ResponseBody
    @RequestMapping("/delete")
    public CommonResult delete(String path) {
        FileUtil.del(storegePath+path);
        return CommonResult.ok();
    }
}
