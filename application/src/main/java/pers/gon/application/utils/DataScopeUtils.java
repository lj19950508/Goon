package pers.gon.application.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.jpa.domain.Specification;
import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.domain.upms.service.IUpmsMenuService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class DataScopeUtils {
    static IUpmsMenuService upmsMenuService = SpringUtil.getBean(IUpmsMenuService.class);


    public static int DS_ALL = 0;//全部
    public static int DS_EQ_SELF = 1 ;//自己
    public static int DS_EQ_DEPT_NE_SELF = 2 ;//本级不含自己
    public static int DS_EQ_DEPT = 3 ; //本级
    public static int DS_LT_DEPT = 4; //下级
    public static int DS_LT_DEPT_EQ_SELF = 5; //下级+自己
    public static int DS_LE_DEPT_NE_SELF = 6; //本级不含自己+下级
    public static int DS_LE_DEPT = 7;  //本级+下级
    static Map<String,Integer> rule = new HashMap<>();

    //自定义权限加法
    static{
        // 1+1 => 1   1+2 => 3   1+3 => 3  1+4 => 5  1+5 => 5  1+6 =>7
        // 2+2 => 2   2+3 => 3   2+4 => 6  2+5 => 7  2+6 => 6
        // 3+3 => 3   3+4 => 7   3+5 => 7  3+6 => 6
        // 4+4 => 4   4+5 => 5   4+6 => 6
        // 5+5 => 5   5+6 => 7
        // 6+6 => 6
        //改成不区分顺序
        rule.put("1,1",DS_EQ_SELF);
        rule.put("1,2",DS_EQ_DEPT);
        rule.put("1,3",DS_EQ_DEPT);
        rule.put("1,4",DS_LT_DEPT_EQ_SELF);
        rule.put("1,5",DS_LT_DEPT_EQ_SELF);
        rule.put("1,6",DS_LE_DEPT);
        rule.put("2,2",DS_EQ_DEPT_NE_SELF);
        rule.put("2,3",DS_EQ_DEPT);
        rule.put("2,4",DS_LE_DEPT_NE_SELF);
        rule.put("2,5",DS_LE_DEPT);
        rule.put("2,6",DS_LE_DEPT_NE_SELF);
        rule.put("3,3",DS_EQ_DEPT);
        rule.put("3,4",DS_LE_DEPT);
        rule.put("3,5",DS_LE_DEPT);
        rule.put("3,6",DS_LE_DEPT_NE_SELF);
        rule.put("4,4",DS_LT_DEPT);
        rule.put("4,5",DS_LT_DEPT_EQ_SELF);
        rule.put("4,6",DS_LE_DEPT_NE_SELF);
        rule.put("5,5",DS_LT_DEPT_EQ_SELF);
        rule.put("5,6",DS_LE_DEPT);
        rule.put("6,6",DS_LE_DEPT);
    }

    /**
     * 自定义权限加法
     * @param values
     * @return
     */
    public static int getDsValue(Set<Integer> values){
        List<Integer> valueList = new ArrayList<>(values);
        int finalValue = valueList.get(0);
        for (int i = 0; i < valueList.size(); i++) {
            finalValue = combine(finalValue,valueList.get(i));
        }
        return finalValue;
    }

    public static int combine(int value1,int value2){
        if(value1<value2){
            return rule.get(value1+","+value2);
        }else {
            return rule.get(value2+","+value1);
        }
    }

    /**
     * 当前角色当前菜单默认动态的DataScope
     * 获取默认的dataScope 首先查看 datascope表 currentUrl = 对应的菜单 对应的dascope  的 如果有则取该值， 否则取当前角色默认的datascope
     * @return
     */
    public static Specification dataScope(HttpServletRequest request){
        String url = request.getServletPath();
        int start =  url.indexOf("/",1);
        int end =  url.lastIndexOf("/");
        url = url.substring(start,end);
        UpmsMenu upmsMenu = upmsMenuService.findByUrl(url);
        String menuCode;
        if(upmsMenu==null){
            menuCode="";
        }else{
            menuCode = upmsMenu.getCode();
        }
        return dataScope(menuCode);
    }

    /**
     * 动态datascope 根据后台获取
     * 根据DatascopeCode和当前角色找到 value 并找到对应的dataScope
     * @param dataScopeCode
     * @return
     */
    public static Specification dataScope(String dataScopeCode){
        Set<Integer> dataScopeValue = new HashSet<>();

        Set<UpmsRole> roles = CurrentAdminUtils.roles();
        //寻找数据权限关联表的数据权限值
        roles.forEach(roleItem->{
            roleItem.getDatascopes().forEach(dsItem->{
                if(dataScopeCode.equals(dsItem.getCode())){
                    dataScopeValue.add(dsItem.getValue());
                }
            });
        });
        //如果没值则取默认
        if(dataScopeValue.isEmpty()){
            roles.forEach(roleItem->{
                dataScopeValue.add(roleItem.getDatascopeValue());
            });
        }
        //包含0位全部
        if(dataScopeValue.isEmpty() || dataScopeValue.contains(DS_ALL)){
            return dataScope(DS_ALL);
        }
        //7是除了全部以外最高级别
        if(dataScopeValue.contains(DS_LE_DEPT)){
            return dataScope(DS_LE_DEPT);
        }
        //自定义算法计算
        return dataScope(DataScopeUtils.getDsValue(dataScopeValue));
    }

    /**
     * 全局DataScope
     * 生成DataScope
     * @param
     * @return
     */
    public static Specification dataScope(Integer value){
        Specification specification = (Specification) (root, cq, cb) -> {
            if(value==DS_ALL){
                //全部数据
            }else if(value==DS_EQ_SELF){
                //自己 eq_self
                cq.where(cb.equal(root.get("createBy").get("id"), CurrentAdminUtils.id()));
            }else if(value==DS_EQ_DEPT_NE_SELF){
                //本级不含自己
                cq.where(cb.equal(root.get("createBy").get("dept").get("code"), CurrentAdminUtils.deptCode()));
                cq.where(cb.notEqual(root.get("createBy").get("id"), CurrentAdminUtils.id()));
            }else if(value==DS_EQ_DEPT){
                cq.where(cb.equal(root.get("createBy").get("dept").get("code"), CurrentAdminUtils.deptCode()));
            }else if(value==DS_LT_DEPT){
                cq.where(cb.like(root.get("createBy").get("dept").get("code"), CurrentAdminUtils.deptCode()+":%"));
            }else if (value==DS_LT_DEPT_EQ_SELF){
                //下级包含自己
                cq.where(cb.like(root.get("createBy").get("dept").get("code"), CurrentAdminUtils.deptCode()+":%"));
                cq.where(cb.equal(root.get("createBy").get("id"), CurrentAdminUtils.id()));
            } else if(value==DS_LE_DEPT_NE_SELF){
                // 本级以及下级不含自己  le_dept_ne_self
                cq.where(cb.like(root.get("createBy").get("dept").get("code"), CurrentAdminUtils.deptCode()+"%"));
                cq.where(cb.notEqual(root.get("createBy").get("id"), CurrentAdminUtils.id()));
            }else if(value==DS_LE_DEPT){
                //本级以及下级 le_dept
                cq.where(cb.like(root.get("createBy").get("dept").get("code"), CurrentAdminUtils.deptCode()+"%"));
            }
            return cq.getRestriction();
        };
        return specification;
    }


    public static Integer getDatascopeValue(UpmsRole upmsRole, String menuCode) {
        final Integer[] dataScopeValue = new Integer[1];

        //寻找数据权限关联表的数据权限值
        upmsRole.getDatascopes().forEach(dsItem->{
            if(menuCode.equals(dsItem.getCode())){
                dataScopeValue[0] = dsItem.getValue();
            }
        });
        //如果没值则取默认
        if(dataScopeValue[0] ==null){
                dataScopeValue[0] = upmsRole.getDatascopeValue();
        }
        return dataScopeValue[0];
    }
}
