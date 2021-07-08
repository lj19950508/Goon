package pers.gon.manage.file;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.gon.application.utils.DataScopeUtils;
import pers.gon.domain.upms.entity.UpmsDept;
import pers.gon.infrastructure.common.entity.CommonResult;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("${global.adminpath}/file")
public class FileController {

    /**
     * 后台上传文件
     * @param file 文件
     * @param uploadPath 上传的路径
     * @return
     */
    @ResponseBody
    @PostMapping("/upload/{uploadPath}")
    public CommonResult upload(MultipartFile file, @PathVariable(value = "uploadPath") String uploadPath) {
        //todo 1.file存到相应物理路径  ip:port/file/path
        //todo 2.file信息存储的数
        //

        return CommonResult.ok();
    }

}
