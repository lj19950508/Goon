package pers.gon.domain.upms.converter;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import pers.gon.domain.upms.entity.UpmsDept;
import pers.gon.domain.upms.repository.UpmsDeptRepository;
import pers.gon.infrastructure.common.repository.BaseRepository;

public class UpmsDeptConverter implements Converter<UpmsDept> {
    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public UpmsDept convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        UpmsDeptRepository upmsDeptRepository  = SpringUtil.getBean(UpmsDeptRepository.class);
        return upmsDeptRepository.findByName(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(UpmsDept upmsDept, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(upmsDept.getName());
    }
}
