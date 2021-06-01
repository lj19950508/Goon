package pers.gon.domain.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pers.gon.domain.DataEntity;
import pers.gon.domain.sys.repository.SysDictRepository;
import pers.gon.domain.sys.vo.SysDictItem;
import pers.gon.infrastructure.common.valid.InsertGroup;
import pers.gon.infrastructure.common.valid.SaveGroup;
import pers.gon.infrastructure.common.valid.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

/**
 * @Author: Gon
 * @Date: 2020/9/26 13:59
 **/
@Entity
@Getter
@Setter
@Table(name = "sys_dict")
@SQLDelete(sql = "update sys_dict set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
public class SysDict extends DataEntity {
    @NotEmpty(message = "字典名称不能未空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '字典名称'")
    private String name;

    @NotEmpty(message = "字典编码不能未空",groups = SaveGroup.class)
    @Unique(repository = SysDictRepository.class,fieldName = "code",groups = InsertGroup.class,message = "字典编码已存在")
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '字典编码'")
    private String code;

    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;

    @Embedded
    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(
            name = "sys_dict_item",
            joinColumns = @JoinColumn(name = "dict_id"),
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)
    )
    List<SysDictItem> dictItems;

}
