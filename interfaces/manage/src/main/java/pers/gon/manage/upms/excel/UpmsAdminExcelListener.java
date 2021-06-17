package pers.gon.manage.upms.excel;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.domain.upms.service.IUpmsAdminService;
import pers.gon.domain.upms.service.UpmsAdminService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UpmsAdminExcelListener extends AnalysisEventListener<UpmsAdminExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    List<UpmsAdminExcel> list = new ArrayList<UpmsAdminExcel>();
    private IUpmsAdminService upmsAdminService =  SpringUtil.getBean(UpmsAdminService.class);

    public UpmsAdminExcelListener(){
    }

    @Override
    public void invoke(UpmsAdminExcel data, AnalysisContext analysisContext) {
        log.info("数据:{}",data);
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        List<UpmsAdmin> upmsAdmins = new ArrayList<>();

        list.forEach(item->{
            UpmsAdmin upmsAdmin = new UpmsAdmin();
            upmsAdmin.setAccount(item.getAccount());
            upmsAdmins.add(upmsAdmin);
        });
        upmsAdminService.insertInBatch(upmsAdmins,BATCH_COUNT);
    }
}
