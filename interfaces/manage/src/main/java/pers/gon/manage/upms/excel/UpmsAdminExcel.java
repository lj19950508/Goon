package pers.gon.manage.upms.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import pers.gon.domain.upms.entity.UpmsAdmin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class UpmsAdminExcel {
    @ExcelProperty("账号")
    private String account;
    @ExcelProperty("昵称")
    private String nickname;
    @ExcelProperty("部门")
    private String deptName;
    @ExcelProperty("角色")
    private String roleName;


    public static List<UpmsAdminExcel> transform(List<UpmsAdmin> upmsAdmins){
        List<UpmsAdminExcel> upmsAdminExcels = new ArrayList<>();
        upmsAdmins.forEach(item->{
            UpmsAdminExcel upmsAdminExcel = new UpmsAdminExcel();
            upmsAdminExcel.setAccount(item.getAccount());
            upmsAdminExcel.setNickname(item.getNickname());
            upmsAdminExcel.setDeptName(item.getDept().getName());
            String roleName = item.getRoles().stream().map(roleItem-> roleItem.getName()).collect(Collectors.joining(","));
            upmsAdminExcel.setRoleName(roleName);
            upmsAdminExcels.add(upmsAdminExcel);
        });
        return upmsAdminExcels;
    }

}
