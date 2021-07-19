package pers.gon.manage.file;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.gon.infrastructure.common.entity.CommonResult;

import java.io.File;
import java.util.Date;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/file")
public class FileController {
    @Value("${file.path}")
    String storegePath;

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
        int month = DateUtil.month(now);
        int year = DateUtil.year(now);
        int day = DateUtil.dayOfMonth(now);

        //如果文件名字重复自动重命名

        File saveFile =  FileUtil.touch(storegePath+"/"+uploadPath+"/"+year+"/"+month+"/"+day+"/"+file.getOriginalFilename());
        //在某个路径下 +uploadpath  +年月日+文件名创建文件

        file.transferTo(saveFile);

        return CommonResult.ok();
    }

}
