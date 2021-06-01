package pers.gon.infrastructure.common.idgenerator;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.*;
import org.hibernate.id.enhanced.TableGenerator;

import java.io.Serializable;

/**
 * @Author: Gon
 * @Date: 2020/9/10 23:50
 **/
public class SnowFlakeId extends UUIDGenerator {
    static Snowflake snowflake = IdUtil.createSnowflake(1,1);

    @Override
     public Serializable generate(SharedSessionContractImplementor session, Object object)  {
             String id = snowflake.nextIdStr();
             if (StrUtil.isNotEmpty(id)) {
                 return  id;
             }
             return super.generate(session, object);
    }
}
