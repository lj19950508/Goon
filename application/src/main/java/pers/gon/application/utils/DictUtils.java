package pers.gon.application.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import pers.gon.domain.sys.service.ISysDictService;
import pers.gon.domain.sys.vo.SysDictItem;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Gon
 * @Date: 2020/9/27 19:37
 **/
public class DictUtils {
    static ISysDictService dictService = SpringUtil.getBean(ISysDictService.class);

    public static Set<SysDictItem> get(String code) {
        return dictService.findByCode(code).getDictItems().stream()
                .sorted(Comparator.comparing(SysDictItem::getSort))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public static String getJSON(String code) {
        return JSONUtil.toJsonStr(get(code));
    }

    public static Integer getValue(String code,String name) {
        List<SysDictItem> items = dictService.findByCode(code).getDictItems();
        Integer value =  items.stream().filter(item-> item.getName().equals(name))
                .map(SysDictItem::getValue)
                .collect(Collectors.toList()).get(0);
        return value;
    }

    public static String getName(String code,Integer value) {
        List<SysDictItem> items = dictService.findByCode(code).getDictItems();
        String name =  items.stream().filter(item-> item.getValue().equals(value))
                .map(SysDictItem::getName)
                .collect(Collectors.toList()).get(0);
        return name;
    }
}
