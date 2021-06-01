package pers.gon.infrastructure.common.config.global;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
@ConfigurationProperties(prefix="global")
public class GlobalProperties {

    private String adminPath;
    private String apiPath;
    private String adminSystemName;




}
