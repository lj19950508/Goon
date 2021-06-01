package pers.gon.infrastructure.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartListener implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Goon is start up completed!");
    }
}
