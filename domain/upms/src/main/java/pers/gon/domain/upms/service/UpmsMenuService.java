package pers.gon.domain.upms.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.domain.upms.event.MenuDeleteEvent;
import pers.gon.domain.upms.event.MenuCreateEvent;
import pers.gon.domain.upms.repository.UpmsMenuRepository;
import pers.gon.domain.upms.repository.UpmsRoleRepository;
import pers.gon.infrastructure.common.service.BaseService;

import java.util.List;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:14
 **/
@Service
public class UpmsMenuService extends BaseService<UpmsMenuRepository, UpmsMenu, String> implements IUpmsMenuService {

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    UpmsMenuRepository upmsMenuRepository;


    @Override
    public UpmsMenu save(UpmsMenu upmsMenu) {
        boolean isNewRecord = upmsMenu.isNewRecord();
        UpmsMenu menu = super.save(upmsMenu);
        if (isNewRecord) {
            publisher.publishEvent(new MenuCreateEvent(this, menu));
        }
        return menu;
    }

    @Override
    public void deleteById(String id) {
        UpmsMenu upmsMenu = this.findById(id);
        publisher.publishEvent(new MenuDeleteEvent(this, upmsMenu));
        super.deleteById(id);
    }

    @Override
    public UpmsMenu findByUrl(String url) {
        return upmsMenuRepository.findByUrl(url);
    }
}
