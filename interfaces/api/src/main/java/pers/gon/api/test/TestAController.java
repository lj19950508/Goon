package pers.gon.api.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;


@Slf4j
@Controller
@RequestMapping("${global.apipath}/test")
public class TestAController extends BaseController {

    @ResponseBody
    @GetMapping("/test1")
    public CommonResult test() {

        return CommonResult.ok("123");
    }



}
