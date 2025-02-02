package com.blusalt.blusalt.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext context;


	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}

	public static <T> T getBean(String beanClass) {
		return (T) context.getBean(beanClass);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		// store ApplicationContext reference to access required beans later on
		SpringContext.context = context;
	}
}
