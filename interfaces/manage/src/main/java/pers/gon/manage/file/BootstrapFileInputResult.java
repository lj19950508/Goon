package pers.gon.manage.file;

import lombok.Data;

@Data
public class BootstrapFileInputResult {
    private String caption;
    private String downloadUrl;
    private String exif;
    private String key;
    private Long size;
    private String url;
}
