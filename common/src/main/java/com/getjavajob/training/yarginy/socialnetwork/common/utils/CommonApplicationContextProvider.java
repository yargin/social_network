package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.util.Objects.isNull;

public final class CommonApplicationContextProvider {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("commonSpringConfig.xml");

    private CommonApplicationContextProvider() {
    }

    public static ApplicationContext getContext() {
        if (isNull(context)) {
            context = new ClassPathXmlApplicationContext("commonSpringConfig.xml");
        }
        return context;
    }
}
