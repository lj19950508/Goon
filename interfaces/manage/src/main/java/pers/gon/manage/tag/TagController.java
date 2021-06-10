package pers.gon.manage.tag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.gon.infrastructure.common.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Gon
 * @Date: 2020/9/21 21:58
 **/
@Slf4j
@Controller
@RequestMapping("${global.adminpath}/tag")
public class TagController extends BaseController {

    @RequestMapping(value = "treeselect")
    public String treeselect(HttpServletRequest request, Model model) {
        model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
        model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
        model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
        model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
        model.addAttribute("isAll", request.getParameter("isAll")); 	// 是否读取全部数据，不进行权限过滤
        model.addAttribute("allowSearch", request.getParameter("allowSearch"));	// 是否显示查找
        model.addAttribute("notAllowSelectParent", request.getParameter("notAllowSelectParent"));	// 是否允许选择父节点
        model.addAttribute("notAllowSelectRoot", request.getParameter("notAllowSelectRoot"));	// 是否允许选择根节点
        return "modules/common/treeSelect.html";
    }

    @RequestMapping(value = "iconselect")
    public String iconselect(HttpServletRequest request, Model model) {
        model.addAttribute("value", request.getParameter("value"));
        return "modules/common/tagIconselect.html";
    }
}
