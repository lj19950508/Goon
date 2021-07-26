package pers.gon.manage.map;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/scrum/map")
public class MapController {

    @RequiresPermissions("SCRUM:MAP:LIST")
    @RequestMapping("")
    public String index(Model model) {
        return "modules/scrum/map/map.html";
    }

}
