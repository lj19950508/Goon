package pers.gon.domain.upms.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.domain.upms.service.IUpmsMenuService;
import pers.gon.domain.upms.service.IUpmsRoleService;

import javax.transaction.Transactional;
import java.util.Set;

@Slf4j
//使用了该注解则默认用cglib
@Configuration
public class MenuEventListener  {

    @Autowired
    IUpmsRoleService roleService;

    @Autowired
    IUpmsMenuService sysMenuService;
    @EventListener
    public void onApplicationEvent(MenuCreateEvent menuCreateEvent) {
        UpmsMenu menu = menuCreateEvent.getMenu();
        log.info("新增菜单同步给超管");
        UpmsRole upmsRole = roleService.findByCode("superadmin");
        Set<UpmsMenu> menuList =  upmsRole.getMenus();
        menuList.add(menu);
        roleService.save(upmsRole);
    }
    @Transactional
    @EventListener
    public void onApplicationEvent(MenuDeleteEvent menuDeleteEvent) {
        roleService.findAll().forEach(item->{
            item.getMenus().remove(menuDeleteEvent.getMenu());
            roleService.save(item);

//          不知道上面能否成功删除关系表  roleService.entityManager().merge(item);
        });

    }

    //删除时候删除所有关联

}
