package pers.gon.domain.scrum.dto;

import lombok.Getter;
import lombok.Setter;
import pers.gon.infrastructure.common.valid.SaveGroup;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GencodeConfig {


    private String menuId;

    @NotNull(message = "是否生成Controller不能为空",groups = SaveGroup.class)
    private Boolean controller;
    @NotNull(message = "是否生成Excel不能为空",groups = SaveGroup.class)
    private Boolean excel;
    @NotNull(message = "是否生成Entity不能为空",groups = SaveGroup.class)
    private Boolean entity;
    @NotNull(message = "是否生成Service不能为空",groups = SaveGroup.class)
    private Boolean service;
    @NotNull(message = "是否生成Repository不能为空",groups = SaveGroup.class)
    private Boolean repositroy;
    @NotNull(message = "是否生成Page不能为空",groups = SaveGroup.class)
    private Boolean page;
}
