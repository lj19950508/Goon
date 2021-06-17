package pers.gon.domain.upms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import pers.gon.domain.DataEntity;
import pers.gon.domain.upms.repository.UpmsDeptRepository;
import pers.gon.infrastructure.common.valid.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @Author: Gon
 * @Date: 2020/8/26 23:55
 *
 **/

@Entity
@Getter
@Setter
@Table(name = "upms_dept")
@SQLDelete(sql = "update upms_dept set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
@org.hibernate.annotations.Table(appliesTo = "upms_dept",comment = "部门机构表")
public class UpmsDept extends DataEntity {
    @NotEmpty(message = "部门编码不能未空",groups = SaveGroup.class)
    @Unique(repository = UpmsDeptRepository.class,fieldName = "code",groups = SaveGroup.class,message = "部门编码已存在")
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '部门编码'")
    private String code;
    @Unique(repository = UpmsDeptRepository.class,fieldName = "name",groups = SaveGroup.class,message = "部门名称已存在")
    @NotEmpty(message = "部门名称不能未空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '部门名称'")
    private String name;

    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;

    @JsonIgnoreProperties(value = {"updateBy","createBy"})
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private UpmsDept parent;

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Set<UpmsDept> children;


}
