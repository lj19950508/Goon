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
    private String type;

    public void setTypeByFilename(){
        String suffix = FileUtil.getSuffix(caption);
        switch (suffix){
            case "jpg":this.setType("image");break;
            case "jpeg":this.setType("image");break;
            case "png":this.setType("image");break;
            case "gif":this.setType("image");break;

            case "txt":this.setType("text");break;
            case "pdf":this.setType("pdf");break;
            case "html":this.setType("html");break;
            //gdocs//office//flash//video//audio
            default: this.setType("other");
        }
    }
}
