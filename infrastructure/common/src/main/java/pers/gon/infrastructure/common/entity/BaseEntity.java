package pers.gon.infrastructure.common.entity;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass //表明这是父类，可以将属性映射到子类中使用JPA生成表
public class BaseEntity implements Serializable {
	@Id
	@Column(columnDefinition = " varchar(32) default '' comment 'ID'")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
	@GenericGenerator(name = "custom-id", strategy = "pers.gon.infrastructure.common.idgenerator.SnowFlakeId")
	private String id;

	@JsonIgnore
	public boolean isNewRecord(){
		return StrUtil.isEmpty(this.id) ?true:false;
	}
}
