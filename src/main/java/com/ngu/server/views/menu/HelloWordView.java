package com.ngu.server.views.menu;

import com.ngu.server.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "hello", layout = MainLayout.class)
public class HelloWordView extends VerticalLayout implements MenuItem, InitializingBean {

    H1 viewTitle = new H1("HelloWord");

    @Override
    public void afterPropertiesSet() throws Exception {
        add(viewTitle);
    }

    @Override
    public String getText() {
        return "HelloWord";
    }

    @Override
    public String getIconClass() {
        return "la la-columns";
    }

    @Override
    public Class<? extends com.vaadin.flow.component.Component> getView() {
        return this.getClass();
    }
}
