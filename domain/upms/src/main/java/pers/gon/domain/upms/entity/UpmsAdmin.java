package pers.gon.domain.upms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pers.gon.domain.DataEntity;
import pers.gon.domain.upms.repository.UpmsAdminRepository;
import pers.gon.domain.upms.repository.UpmsDeptRepository;
import pers.gon.infrastructure.common.valid.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: Gon
 * @Date: 2020/8/26 23:55
 * 这是个聚合根
 **/

@Entity
@Getter
@Setter
@Table(name = "upms_admin")
@SQLDelete(sql = "update upms_admin set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
@org.hibernate.annotations.Table(appliesTo = "upms_admin",comment = "后台用户表")
@Unique(repository = UpmsAdminRepository.class,fieldName = "account",groups = SaveGroup.class,message = "账户已存在")
public class UpmsAdmin extends DataEntity {

    //只在SaveGroup时候生效
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '账户'")
    private String account;

    @JsonIgnore
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '密码'")
    private String password;
    @JsonIgnore
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '密码盐'")
    private String salt;

    @Column(columnDefinition = " varchar(32) default '' comment '昵称'")
    private String nickname;

    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;

    @Exist(groups = SaveGroup.class,repository = UpmsDeptRepository.class,message = "部门不存在")
    @JsonIgnoreProperties(value = {"updateBy","createBy","parent","children"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private UpmsDept dept;

    @JsonIgnoreProperties(value = {"updateBy","createBy"})
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "upms_admin_role",
            joinColumns = { @JoinColumn(name = "admin_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "role_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)),
            })
    Set<UpmsRole> roles;


}
