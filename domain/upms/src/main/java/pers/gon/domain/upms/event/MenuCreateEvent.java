package pers.gon.domain.upms.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import pers.gon.domain.upms.entity.UpmsMenu;

@Getter
@Setter
public class MenuCreateEvent extends ApplicationEvent {

    private UpmsMenu menu;

    public MenuCreateEvent(Object source, UpmsMenu upmsMenu) {
         super(source);
         this.menu = upmsMenu;
     }


}
