package pers.gon.domain.upms.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import pers.gon.domain.upms.entity.UpmsMenu;

@Getter
@Setter
public class MenuDeleteEvent extends ApplicationEvent {

    private UpmsMenu menu;

    public MenuDeleteEvent(Object source, UpmsMenu menu) {
         super(source);
         this.menu = menu;
     }


}
