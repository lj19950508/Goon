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

/**
 * @Author: Gon
 * @Date: 2020/8/26 23:55
 **/

@Entity
@Getter
@Setter
@Table(name = "upms_datascope")
@SQLDelete(sql = "update upms_datascope set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
@org.hibernate.annotations.Table(appliesTo = "upms_datascope",comment = "数据权限表")
public class UpmsDatascope extends DataEntity   {
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '数据权限编码'")
    private String code;
    @Column(nullable = false,columnDefinition = " varchar(32) default '' comment '数据全程描述'")
    private String name;
    @Column(nullable = false,columnDefinition = " char(2) default '' comment '数据权限值'")
    private Integer value;
    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;
}
