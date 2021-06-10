package pers.gon.domain.scrum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pers.gon.domain.DataEntity;
import pers.gon.domain.scrum.vo.GencodeEntityItem;
import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.infrastructure.common.valid.SaveGroup;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "gencode_entity")
@SQLDelete(sql = "update gencode_entity set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
public class GencodeEntity extends DataEntity {
    @NotEmpty(message = "实体名称不能未空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '实体名称'")
    private String entityName;
    @NotEmpty(message = "实体描述不能未空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(255) default '' comment '实体描述'")
    private String entityDesc;

    @NotNull(message = "模板类型不能为空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " char(2) default '' comment '模板类型'")
    private Integer templateType;
    @NotNull(message = "是否逻辑删除不能为空",groups = SaveGroup.class)
    @Column(columnDefinition = "char(1) comment '是否逻辑删除'")
    private Boolean isLogicDelete;


    //one to one
    @OneToOne
    @JoinColumn(name = "parent_menu_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private UpmsMenu parentMenu;

    @NotEmpty(message = "模块名称不能为空",groups = SaveGroup.class)
    @Column(columnDefinition = "varchar(32) comment '模块名称'")
    private String moduleName;



    //字段列表
    @Embedded
    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(
            name = "gencode_entity_item",
            joinColumns = @JoinColumn(name = "entity_id"),
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)
    )
    private List<GencodeEntityItem> items;

    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;


}
