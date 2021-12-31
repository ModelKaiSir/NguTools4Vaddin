package com.ngu.server.views.form;

import com.ngu.server.data.dto.DataSourceConfigInfo;
import com.ngu.server.data.service.GenerateServerConfigService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;

import java.util.Collections;

public class ServerConfigForm extends Div {

    private ComboBox<String> dataBaseUrl = new ComboBox<>("数据库地址");
    private ComboBox<String> driverName = new ComboBox<>("Driver");
    private TextField userName = new TextField("账号");
    private PasswordField password = new PasswordField("密码");

    private Button save = new Button("保存");

    private Binder<DataSourceConfigInfo> infoBinder = new Binder<>(DataSourceConfigInfo.class);

    private GenerateServerConfigService generateServerConfigService;

    public ServerConfigForm(GenerateServerConfigService generateServerConfigService) {

        add(createFormLayout());
        add(createToolbar());

        dataBaseUrl.setItems(generateServerConfigService.getServerConfig().getDataBaseUrl());
        driverName.setItems(generateServerConfigService.getServerConfig().getDriverName());

        infoBinder.forField(dataBaseUrl).asRequired("不能为空").bind(DataSourceConfigInfo::getDataBaseUrl, DataSourceConfigInfo::setDataBaseUrl);
        infoBinder.forField(driverName).asRequired("不能为空").bind(DataSourceConfigInfo::getDriverName, DataSourceConfigInfo::setDriverName);
        infoBinder.forField(userName).asRequired("不能为空").bind(DataSourceConfigInfo::getUserName, DataSourceConfigInfo::setUserName);
        infoBinder.forField(password).asRequired("不能为空").bind(DataSourceConfigInfo::getPassword, DataSourceConfigInfo::setPassword);

        infoBinder.setBean(generateServerConfigService.getServerConfig());
        save.addClickListener(this::save);

        this.generateServerConfigService = generateServerConfigService;
    }

    private Component createToolbar() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(save);
        return layout;
    }

    private Component createFormLayout() {

        FormLayout layout = new FormLayout();
        layout.add(dataBaseUrl, driverName, userName, password);
        layout.setHeightFull();
        return layout;
    }

    public void save(ClickEvent<Button> event) {
        BinderValidationStatus<DataSourceConfigInfo> status = infoBinder.validate();
        if (status.isOk()) {
            generateServerConfigService.setServerConfig(infoBinder.getBean());
            Notification.show("保存成功");
        }
    }
}
