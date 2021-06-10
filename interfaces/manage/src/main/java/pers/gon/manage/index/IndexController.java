package pers.gon.manage.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.gon.infrastructure.common.controller.BaseController;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}")
public class IndexController extends BaseController {


    @RequestMapping("")
    public String loginPage(Model model){
        return "include/_index.html";
    }





}
