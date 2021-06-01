package pers.gon.domain.sys.vo;

import lombok.Getter;
import lombok.Setter;
import pers.gon.domain.DataEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Author: Gon
 * @Date: 2020/9/26 19:59
 **/

@Embeddable
@Getter
@Setter
public class SysDictItem  {
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '字典项名称'")
    private String name;
    @Column(nullable = false,columnDefinition = " char(2)  comment '字典项值'")
    private Integer value;
    @Column(nullable = false,columnDefinition = " int(11)  comment '字典项排序'")
    private Integer sort;
}