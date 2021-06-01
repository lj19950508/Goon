package pers.gon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.infrastructure.common.entity.BaseEntity;

import javax.persistence.*;
import javax.persistence.ForeignKey;
import java.util.Date;

/**
 * @Author: Gon
 * @Date: 2020/8/31 23:52
 **/

@Getter
@Setter
@MappedSuperclass //表明这是父类，可以将属性映射到子类中使用JPA生成表
@DynamicUpdate //动态赋值
@DynamicInsert //动态插入
@EntityListeners(AuditingEntityListener.class)
public class DataEntity extends BaseEntity {

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "DATETIME comment '创建时间'")
    protected Date createTime; //创建时间

    @LastModifiedDate
    @Column(columnDefinition = "DATETIME comment '更新时间'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime; //创建时间


    //解决循环嵌套的问题
    @JsonIgnoreProperties(value = {"createBy","updateBy"})
    @CreatedBy
    @ManyToOne(fetch= FetchType.EAGER) //多个
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    protected UpmsAdmin createBy;

    @JsonIgnoreProperties(value = {"updateBy","createBy"})
    @LastModifiedBy
    @ManyToOne(fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    protected UpmsAdmin updateBy;




}
