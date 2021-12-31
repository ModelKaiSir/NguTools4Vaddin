package com.ngu.server.views.menu;

import com.ngu.server.data.service.GenerateServerConfigService;
import com.ngu.server.views.MainLayout;
import com.ngu.server.views.form.ServerConfigForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;

@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "config", layout = MainLayout.class)
public class GenerateServerConfigView extends VerticalLayout implements InitializingBean, MenuItem{

    @Resource
    private GenerateServerConfigService generateServerConfigService;

    private ServerConfigForm serverConfigForm;

    @Override
    public String getText() {
        return "代码生成服务数据库配置";
    }

    @Override
    public String getIconClass() {
        return "la la-columns";
    }

    @Override
    public Class<? extends Component> getView() {
        return this.getClass();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        setHorizontalComponentAlignment(Alignment.END);
        setSizeFull();
        serverConfigForm = new ServerConfigForm(generateServerConfigService);
        add(serverConfigForm);
    }
}
