package pers.gon.domain.upms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pers.gon.domain.DataEntity;
import pers.gon.domain.upms.repository.UpmsRoleRepository;
import pers.gon.infrastructure.common.valid.InsertGroup;
import pers.gon.infrastructure.common.valid.Unique;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: Gon
 * @Date: 2020/8/26 23:55
 *
 **/

@Entity
@Getter
@Setter
@Table(name = "upms_role")
@SQLDelete(sql = "update upms_role set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
@org.hibernate.annotations.Table(appliesTo = "upms_role",comment = "后台角色表")
public class UpmsRole extends DataEntity {

    @Unique(repository = UpmsRoleRepository.class,fieldName = "code",groups = InsertGroup.class,message = "角色编码已存在")
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '角色编码'")
    private String code;
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '角色名称'")
    private String name;

    @Column(nullable = false,columnDefinition = " char(2) default '0' comment '默认数据权限'")
    private Integer datascopeValue;

    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;

    @JsonIgnoreProperties(value = {"updateBy","createBy"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "upms_role_menu", joinColumns = { @JoinColumn(name = "role_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))},inverseJoinColumns = {@JoinColumn(name = "menu_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)) })
    Set<UpmsMenu> menus;


    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "role_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    Set<UpmsDatascope> datascopes;


}
