package pers.gon.domain.scrum.vo;

import lombok.Getter;
import lombok.Setter;
import pers.gon.infrastructure.common.valid.SaveGroup;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@Setter
public class GencodeEntityItem {

    //字段名称
    @NotEmpty(message = "字段名称不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '字段名称'")
    private String itemName;
    //字段描述
    @NotEmpty(message = "字段描述不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(64) default '' comment '字段描述'")
    private String itemDesc;
    //对应的Java类型
    @NotEmpty(message = "JAVA类型不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment 'JAVA类型'")
    private String itemType;

    @NotEmpty(message = "SQL类型不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment 'SQL类型'")
    private String sqlType="varchar(255)";

    //0不显示 1文本 2长文本 3富文本 4下拉 5单选 6多选 7 日期 8数字输入框
    @NotEmpty(message = "表单类不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " char(2) default '' comment '模板类型'")
    private Integer formType;

    @Column(nullable = true,columnDefinition = " varchar(32) default '' comment '对应字典编码'")
    private String dictCode;

    //0不查询 1输入框 2下拉 3单选 4多选 5日期范围 6数字
    @NotEmpty(message = "查询类型不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " char(2) default '' comment '查询类型'")
    private Integer queryType;
    //0 eq 1like 2llike 3rlike 4 gt 5 lt 6 ge 7 le 8bettwen
    @Column(nullable = false,columnDefinition = " char(2) default '' comment '查询条件'")
    private Integer queryExp;

    @NotEmpty(message = "列表长度不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " char(4) default '' comment '列表长度'")
    private Integer listLength;

    @NotEmpty(message = "是否列表展示不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " char(1) default '' comment '是否列表展示'")
    private boolean isListShow;
    @Column(nullable = false,columnDefinition = " char(1) default '' comment '是否必填'")
    private boolean isMust;
    @Column(nullable = false,columnDefinition = " char(1) default '' comment '是否可排序'")
    private boolean isSort;
    @Column(nullable = false,columnDefinition = " char(1) default '' comment '是否唯一'")
    private boolean isUnique;


}
