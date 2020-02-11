package com.kevin.cloud.service;

import com.kevin.cloud.provider.SpringContextProviderUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-23 21:46
 **/
@Component("springContextServiceUtil")
public class SpringContextServiceUtil implements ApplicationContextAware {
    /**
     *  Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextServiceUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象 这里重写了bean方法，起主要作用
     */
    public static Object getBean(String beanId) throws BeansException {
        return applicationContext.getBean(beanId);
    }
    /**
     * 同步方法注册bean到ApplicationContext中
     *
     * @param beanName
     * @param clazz
     * @param original bean的属性值
     */
    public static synchronized void setBean(String beanName, Class<?> clazz, Map<String,Object> original) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        if(beanFactory.containsBean(beanName)){
            return;
        }
        //BeanDefinition beanDefinition = new RootBeanDefinition(clazz);
        GenericBeanDefinition definition = new GenericBeanDefinition();
        //类class
        definition.setBeanClass(clazz);
        //属性赋值
        definition.setPropertyValues(new MutablePropertyValues(original));
        //注册到spring上下文
        beanFactory.registerBeanDefinition(beanName, definition);
    }

}
