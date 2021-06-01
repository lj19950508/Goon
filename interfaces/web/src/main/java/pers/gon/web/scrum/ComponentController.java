package pers.gon.web.scrum;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pers.gon.infrastructure.common.controller.BaseController;

@Slf4j
@Controller
@RequestMapping("${global.adminpath}/scrum/component")
public class ComponentController extends BaseController {


    @RequiresPermissions("SCRUM:GENCODE")
    @RequestMapping("")
    public String index(Model model) {
        return "modules/scrum/component/component.html";
    }


}
