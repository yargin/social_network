package com.getjavajob.training.yarginy.socialnetwork.web.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        webCtx.register(MvcConfig.class, SecurityConfig.class, WebSocketConfig.class);
        webCtx.setServletContext(servletContext);

        servletContext.setSessionTimeout(10);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(webCtx));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        ServletRegistration.Dynamic defaultServlet = servletContext.addServlet("default",
                "org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.setLoadOnStartup(1);
        defaultServlet.addMapping("/css/*", "/js/*", "/img/*", "/font/*", "/webjars/*");

        FilterRegistration.Dynamic securityChain = servletContext.addFilter("springSecurityFilterChain",
                DelegatingFilterProxy.class);
        securityChain.setAsyncSupported(true);
        securityChain.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter",
                CharacterEncodingFilter.class);
        encodingFilter.setAsyncSupported(true);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
