package com.ngu.server.views.menu;

import com.ngu.server.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "generate", layout = MainLayout.class)
public class GenerateServerView extends VerticalLayout implements InitializingBean, MenuItem {

    @Override
    public String getText() {
        return "代码生成服务";
    }

    @Override
    public String getIconClass() {
        return "la la-columns";
    }

    @Override
    public Class<? extends com.vaadin.flow.component.Component> getView() {
        return this.getClass();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
