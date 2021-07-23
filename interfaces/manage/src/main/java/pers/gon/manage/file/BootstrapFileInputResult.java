package pers.gon.manage.file;

import cn.hutool.core.io.FileUtil;
import lombok.Data;

@Data
public class BootstrapFileInputResult {
    private String caption;
    private String downloadUrl;
    private String key;
    private Long size;
    private String url;
    private String filetype;


}
