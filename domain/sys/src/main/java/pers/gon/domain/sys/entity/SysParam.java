package pers.gon.domain.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pers.gon.domain.DataEntity;
import pers.gon.domain.sys.repository.SysParamRepository;
import pers.gon.infrastructure.common.valid.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * @Author: Gon
 * @Date: 2020/9/26 13:59
 **/
@Entity
@Getter
@Setter
@Table(name = "sys_param")
@SQLDelete(sql = "update sys_param set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
@Unique(repository = SysParamRepository.class,fieldName = "code",groups = SaveGroup.class,message = "参数编码已存在")
public class SysParam extends DataEntity {
    @NotEmpty(message = "参数名称不能未空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '参数名称'")
    private String name;

    @NotEmpty(message = "参数编码不能未空",groups = SaveGroup.class)
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '参数编码'")
    private String code;


    @NotEmpty(message = "参数值不能未空",groups = SaveGroup.class)
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false,columnDefinition = " longtext  comment '参数值'")
    private String value;

    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;
}
