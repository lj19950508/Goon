package pers.gon.domain.scrum.service;


import freemarker.template.Configuration;
import pers.gon.domain.scrum.entity.GencodeEntity;
import pers.gon.infrastructure.common.service.IBaseService;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: Gon
 * @Date: 2020/9/15 0:14
 **/

public interface IGencodeEntityService extends IBaseService<GencodeEntity,String>{


    void gencode(GencodeEntity gencodeEntity);

    void gencodeForSimpleEntity(GencodeEntity gencodeEntity) throws IOException;

    void genItem(String templatePath, String outPath, String fileName, Map<String,Object> model);

    Configuration getFreemarkerConfig();

    void genmenu(GencodeEntity gencodeEntity);
}
