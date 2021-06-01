package pers.gon.web.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.gon.infrastructure.common.controller.BaseController;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}")
public class HomeController extends BaseController {

    @RequestMapping("/home")
    public String loginPage(Model model){
        return "modules/sys/home/home.html";
    }





}
