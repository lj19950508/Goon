package pers.gon.domain.scrum.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gon.domain.scrum.dto.GencodeConfig;
import pers.gon.domain.scrum.entity.GencodeEntity;
import pers.gon.domain.scrum.repository.GencodeEntityRepositroy;
import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.domain.upms.service.IUpmsMenuService;
import pers.gon.infrastructure.common.entity.CommonResult;
import pers.gon.infrastructure.common.service.BaseService;
import pers.gon.infrastructure.common.utils.FileUtils;
import pers.gon.infrastructure.common.valid.SaveGroup;

import javax.validation.Validator;
import java.io.File;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:14
 **/
@Slf4j
@Service
public class GencodeEntityService extends BaseService<GencodeEntityRepositroy, GencodeEntity,String> implements IGencodeEntityService {


    @Autowired
    IUpmsMenuService upmsMenuService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    protected Validator validator;

    @Override
    public void gencode(GencodeEntity gencodeEntity, GencodeConfig config) {
        if(gencodeEntity.getTemplateType()==0){
            gencodeForSimpleEntity(gencodeEntity,config);
        }else if(gencodeEntity.getTemplateType()==1){
            gencodeForTreeEntity(gencodeEntity,config);
        }
    }

    private void gencodeForTreeEntity(GencodeEntity gencodeEntity, GencodeConfig config) {
        Map<String,Object> genCodeMap = BeanUtil.beanToMap(gencodeEntity);
        //添加额外参数

        genCodeMap.put("upEntityName",StrUtil.upperFirst(gencodeEntity.getEntityName()));
        genCodeMap.put("lowEntityName",StrUtil.lowerFirst(gencodeEntity.getEntityName()));
        genCodeMap.put("camelEntityName",StrUtil.toCamelCase(gencodeEntity.getEntityName()));
        genCodeMap.put("underEntityName",StrUtil.toUnderlineCase(gencodeEntity.getEntityName()));
        genCodeMap.put("excel",config.getExcel());

        if(config.getEntity()==true){
            genItem("treeEntity/entity.ftl",
                    FileUtils.getDomainPath(gencodeEntity.getModuleName())+gencodeEntity.getModuleName()+"/entity/",
                    genCodeMap.get("upEntityName")+".java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+".java",FileUtils.getDomainPath(gencodeEntity.getModuleName())+gencodeEntity.getModuleName()+"/entity/");
        }

        if(config.getService()==true) {
            genItem("treeEntity/iservice.ftl",
                    FileUtils.getDomainPath(gencodeEntity.getModuleName()) + gencodeEntity.getModuleName() + "/service/",
                    "I" + genCodeMap.get("upEntityName") + "Service.java", genCodeMap);
            log.debug("生成了：{},位于:{}", "I" + genCodeMap.get("upEntityName") + "Service.java", FileUtils.getDomainPath(gencodeEntity.getModuleName()) + gencodeEntity.getModuleName() + "/service/");

            genItem("treeEntity/service.ftl",
                    FileUtils.getDomainPath(gencodeEntity.getModuleName()) + gencodeEntity.getModuleName() + "/service/",
                    genCodeMap.get("upEntityName") + "Service.java", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("upEntityName") + "Service.java", FileUtils.getDomainPath(gencodeEntity.getModuleName()) + gencodeEntity.getModuleName() + "/service/");
        }
        if(config.getRepositroy()==true) {
            genItem("treeEntity/repository.ftl",
                    FileUtils.getDomainPath(gencodeEntity.getModuleName())+gencodeEntity.getModuleName()+"/repository/",
                    genCodeMap.get("upEntityName")+"Repository.java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+"Repository.java",FileUtils.getDomainPath(gencodeEntity.getModuleName())+gencodeEntity.getModuleName()+"/repository/");
        }
        if(config.getController()==true) {
            genItem("treeEntity/controller.ftl",
                    FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/",
                    genCodeMap.get("upEntityName")+"Controller.java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+"Controller.java",FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/");
        }
        if(config.getExcel()==true) {
            genItem("treeEntity/excel.ftl",
                    FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/excel/",
                    genCodeMap.get("upEntityName")+"Excel.java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+"Excel.java",FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/excel/");

            genItem("treeEntity/excellistener.ftl",
                    FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/excel/",
                    genCodeMap.get("upEntityName")+"ExcelListener.java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+"ExcelListener.java",FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/excel/");
        }
        if(config.getPage()==true) {
            genItem("treeEntity/form.ftl",
                    FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"),
                    genCodeMap.get("lowEntityName") + "Form.html", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("lowEntityName") + "Form.html", FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"));

            genItem("treeEntity/formjs.ftl",
                    FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"),
                    genCodeMap.get("lowEntityName") + "Form.js", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("lowEntityName") + "Form.js", FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"));
            genItem("treeEntity/page.ftl",
                    FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"),
                    genCodeMap.get("lowEntityName") + ".html", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("lowEntityName") + ".html", FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"));
            genItem("treeEntity/pagejs.ftl",
                    FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"),
                    genCodeMap.get("lowEntityName") + ".js", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("lowEntityName") + ".js", FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"));
        }
    }

    @Override
    public void gencodeForSimpleEntity(GencodeEntity gencodeEntity,GencodeConfig config) {
        Map<String,Object> genCodeMap = BeanUtil.beanToMap(gencodeEntity);
        //添加额外参数

        genCodeMap.put("upEntityName",StrUtil.upperFirst(gencodeEntity.getEntityName()));
        genCodeMap.put("lowEntityName",StrUtil.lowerFirst(gencodeEntity.getEntityName()));
        genCodeMap.put("camelEntityName",StrUtil.toCamelCase(gencodeEntity.getEntityName()));
        genCodeMap.put("underEntityName",StrUtil.toUnderlineCase(gencodeEntity.getEntityName()));
        genCodeMap.put("excel",config.getExcel());

        if(config.getEntity()==true){
            genItem("simpleEntity/entity.ftl",
                    FileUtils.getDomainPath(gencodeEntity.getModuleName())+gencodeEntity.getModuleName()+"/entity/",
                    genCodeMap.get("upEntityName")+".java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+".java",FileUtils.getDomainPath(gencodeEntity.getModuleName())+gencodeEntity.getModuleName()+"/entity/");
        }

        if(config.getService()==true) {
            genItem("simpleEntity/iservice.ftl",
                    FileUtils.getDomainPath(gencodeEntity.getModuleName()) + gencodeEntity.getModuleName() + "/service/",
                    "I" + genCodeMap.get("upEntityName") + "Service.java", genCodeMap);
            log.debug("生成了：{},位于:{}", "I" + genCodeMap.get("upEntityName") + "Service.java", FileUtils.getDomainPath(gencodeEntity.getModuleName()) + gencodeEntity.getModuleName() + "/service/");

            genItem("simpleEntity/service.ftl",
                    FileUtils.getDomainPath(gencodeEntity.getModuleName()) + gencodeEntity.getModuleName() + "/service/",
                    genCodeMap.get("upEntityName") + "Service.java", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("upEntityName") + "Service.java", FileUtils.getDomainPath(gencodeEntity.getModuleName()) + gencodeEntity.getModuleName() + "/service/");
        }
        if(config.getRepositroy()==true) {
            genItem("simpleEntity/repository.ftl",
                    FileUtils.getDomainPath(gencodeEntity.getModuleName())+gencodeEntity.getModuleName()+"/repository/",
                    genCodeMap.get("upEntityName")+"Repository.java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+"Repository.java",FileUtils.getDomainPath(gencodeEntity.getModuleName())+gencodeEntity.getModuleName()+"/repository/");
        }
        if(config.getController()==true) {
            genItem("simpleEntity/controller.ftl",
                    FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/",
                    genCodeMap.get("upEntityName")+"Controller.java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+"Controller.java",FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/");
        }
        if(config.getExcel()==true) {
            genItem("simpleEntity/excel.ftl",
                    FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/excel/",
                    genCodeMap.get("upEntityName")+"Excel.java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+"Excel.java",FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/excel/");

            genItem("simpleEntity/excellistener.ftl",
                    FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/excel/",
                    genCodeMap.get("upEntityName")+"ExcelListener.java",genCodeMap);
            log.debug("生成了：{},位于:{}",genCodeMap.get("upEntityName")+"ExcelListener.java",FileUtils.getWebPath()+gencodeEntity.getModuleName()+"/excel/");
        }
        if(config.getPage()==true) {
            genItem("simpleEntity/form.ftl",
                    FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"),
                    genCodeMap.get("lowEntityName") + "Form.html", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("lowEntityName") + "Form.html", FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"));

            genItem("simpleEntity/formjs.ftl",
                    FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"),
                    genCodeMap.get("lowEntityName") + "Form.js", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("lowEntityName") + "Form.js", FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"));
            genItem("simpleEntity/page.ftl",
                    FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"),
                    genCodeMap.get("lowEntityName") + ".html", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("lowEntityName") + ".html", FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"));
            genItem("simpleEntity/pagejs.ftl",
                    FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"),
                    genCodeMap.get("lowEntityName") + ".js", genCodeMap);
            log.debug("生成了：{},位于:{}", genCodeMap.get("lowEntityName") + ".js", FileUtils.getPagePath() + gencodeEntity.getModuleName() + "/" + genCodeMap.get("lowEntityName"));
        }
    }

    @SneakyThrows
    @Override
    public void genItem(String templatePath, String outPath, String fileName, Map<String, Object> model) {
        Template excelTemp = getFreemarkerConfig().getTemplate(templatePath);
        File file = FileUtils.createFile(outPath,fileName);
        Writer excelOut =  FileUtils.getBufferedWriter(file);
        excelTemp.process(model,excelOut);
        excelOut.close();
    }

    @SneakyThrows
    @Override
    public Configuration getFreemarkerConfig() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

        String templatePath =FileUtils.getTemplatePath();
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        return cfg;
    }

    @Override
    public void genmenu(GencodeEntity gencodeEntity,String parentMenuId) {
        UpmsMenu upmsMenu = new UpmsMenu();
        upmsMenu.setCode(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":LIST");
        upmsMenu.setName(gencodeEntity.getEntityDesc());
        upmsMenu.setPermission(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":LIST");
        upmsMenu.setUrl("/"+gencodeEntity.getModuleName()+"/"+gencodeEntity.getEntityName());
        upmsMenu.setType(1);
        upmsMenu.setSort(0);

        UpmsMenu parent = new UpmsMenu();
        parent.setId(parentMenuId);;
        upmsMenu.setParent(parent);

        Set<UpmsMenu> btns = new HashSet<>();
        UpmsMenu add = new UpmsMenu();
        add.setCode(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":ADD");
        add.setPermission(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":ADD");
        add.setName(gencodeEntity.getEntityDesc()+"-新增");
        add.setSort(0);
        add.setType(2);
        UpmsMenu edit = new UpmsMenu();
        edit.setCode(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName()+":EDIT");
        edit.setPermission(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":EDIT");
        edit.setName(gencodeEntity.getEntityDesc()+"-修改");
        edit.setSort(1);
        edit.setType(2);
        UpmsMenu del = new UpmsMenu();
        del.setCode(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":DEL");
        del.setPermission(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":DEL");
        del.setName(gencodeEntity.getEntityDesc()+"-删除");
        del.setSort(2);
        del.setType(2);
        UpmsMenu view = new UpmsMenu();
        view.setCode(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":VIEW");
        view.setPermission(gencodeEntity.getModuleName().toUpperCase()+":"+gencodeEntity.getEntityName().toUpperCase()+":VIEW");
        view.setName(gencodeEntity.getEntityDesc()+"-查询");
        view.setSort(3);
        view.setType(2);
        btns.add(add);
        btns.add(edit);
        btns.add(del);
        btns.add(view);


        Set set = validator.validate(upmsMenu, SaveGroup.class);
        if(set.size()==0){
            upmsMenuService.save(add);
            upmsMenuService.save(edit);
            upmsMenuService.save(del);
            upmsMenuService.save(view);
            upmsMenu.setChildren(btns);
            upmsMenuService.save(upmsMenu);
        }
    }


}
