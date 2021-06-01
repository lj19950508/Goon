package pers.gon.domain.upms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pers.gon.domain.DataEntity;
import pers.gon.domain.upms.repository.UpmsMenuRepository;
import pers.gon.infrastructure.common.valid.InsertGroup;
import pers.gon.infrastructure.common.valid.Unique;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: Gon
 * @Date: 2020/8/26 23:55
 * 模块 每个模块下有资源List
 **/

@Entity
@Getter
@Setter
@Table(name = "upms_menu")
@SQLDelete(sql = "update upms_menu set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
@org.hibernate.annotations.Table(appliesTo = "upms_menu",comment = "菜单资源表")
public class UpmsMenu extends DataEntity {
    @Unique(repository = UpmsMenuRepository.class,fieldName = "code",groups = InsertGroup.class,message = "菜单编码已存在")
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '菜单编码'")
    private String code;
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '菜单名称'")
    private String name;

    @Column(columnDefinition = " int(11) default '0' comment '排序'")
    private Integer sort;

    @Column(columnDefinition = " varchar(255) default '' comment '链接地址'")
    private String url;

    @Column(columnDefinition = " varchar(16) default '' comment '目标'")
    private String target;

    @Column(columnDefinition = " varchar(32) default '' comment '目标'")
    private String icon;

    @Column(columnDefinition = " varchar(32) default '' comment '类型'")
    private Integer type;

    @Column(columnDefinition = " varchar(255) default '' comment '权限标识'")
    private String permission;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private UpmsMenu parent;


    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Set<UpmsMenu> children;


    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;

    @Override
    public boolean equals(Object o) {
        if(! (o instanceof UpmsMenu)){
            return false;
        }
        UpmsMenu upmsMenu = ((UpmsMenu) o);
        if(o==this){
            return true;
        }
        if(upmsMenu.getId().equals(this.getId())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        if(this.getId()!=null){
            return this.getId().hashCode();
        }else{
            return super.hashCode();
        }
    }


}
