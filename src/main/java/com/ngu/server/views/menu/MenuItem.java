package com.ngu.server.views.menu;

import com.vaadin.flow.component.Component;

public interface MenuItem {

    String getText();

    String getIconClass();

    Class<? extends Component> getView();
}
