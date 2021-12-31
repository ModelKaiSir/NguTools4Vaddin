package com.ngu.server.util;

import com.ngu.server.views.menu.MenuItem;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Licc
 * @Date: 2021/11/23 0023 15:44
 * @Description:
 **/
@Component
public class SpringContextUtil implements ApplicationContextAware {
    
    private static ApplicationContext applicationContext = null;

    public static Collection<MenuItem> getBeanList(Class<MenuItem> menuItemClass) {
        return applicationContext.getBeansOfType(menuItemClass).values();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext  getApplicationContext(){
        return applicationContext;
    }

    /**
     * 适用于springbean使用注解@Service("XXXService")
     * 获取接口对象 参数传入 XXXService
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    /**
     * 适用于springbean使用注解@Service
     * 获取接口对象 参数传入 XXXService.class  不是 XXXServiceImpl.class
     * @param c
     * @return
     */
    public static Object getBean(Class c){
        return applicationContext.getBean(c);
    }
}
