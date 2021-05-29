package com.getjavajob.training.yarginy.socialnetwork.web.configuration;

import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.account.AccountAccessSetter;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.account.AccountOwnerChecker;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.account.AccountOwnerFriendChecker;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common.IdSetter;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common.OneIdInterceptor;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common.SessionAuthChecker;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common.TwoIdsInterceptor;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.dialog.DialogAccessChecker;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group.GroupAccessSetter;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group.GroupMemberModerOwnerChecker;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group.GroupModerOwnerChecker;
import com.getjavajob.training.yarginy.socialnetwork.web.interceptors.message.MessageAccessChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    private final ApplicationContext context;

    @Autowired
    public MvcConfig(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("css");
        registry.addResourceHandler("/js/**").addResourceLocations("js");
        registry.addResourceHandler("/img/**").addResourceLocations("img");
        registry.addResourceHandler("/font/**").addResourceLocations("font");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(context.getBean(SessionAuthChecker.class)).addPathPatterns("/**", "/*").
                excludePathPatterns("/login", "/registration");
        registry.addInterceptor(context.getBean(IdSetter.class)).addPathPatterns("/account/wall", "/account/requests",
                "/account/friends", "/account/dialogs", "/account/groups");
        registry.addInterceptor(context.getBean(OneIdInterceptor.class)).addPathPatterns("/account/**", "/group/wall*",
                "/group/moderators*", "/group/requests*", "/group/members*", "/group/delete*", "/group/update*",
                "/dialog/show*", "/message/*/delete*");
        registry.addInterceptor(context.getBean(TwoIdsInterceptor.class)).addPathPatterns("/message/**", "/dialog/new*",
                "/dialog/create*", "/friendship/*", "/group/moderators/add*", "/group/leave*", "/group/join*",
                "/group/moderators/remove*", "/group/accept*");
        registry.addInterceptor(context.getBean(AccountAccessSetter.class)).addPathPatterns("/dialog/create*",
                "/dialog/new*", "/account/*", "/friendship/accept*", "/friendship/remove*", "/account/delete*",
                "/account/update*");
        registry.addInterceptor(context.getBean(GroupAccessSetter.class)).addPathPatterns("/group/**").
                excludePathPatterns("/group/join*", "/group/create*");
        registry.addInterceptor(context.getBean(DialogAccessChecker.class)).addPathPatterns("/dialog/show*");
        registry.addInterceptor(context.getBean(MessageAccessChecker.class)).addPathPatterns("/message/**");
        registry.addInterceptor(context.getBean(AccountOwnerFriendChecker.class)).addPathPatterns("/account/friends*",
                "/account/groups*");
        registry.addInterceptor(context.getBean(AccountOwnerChecker.class)).addPathPatterns("/account/requests*",
                "/account/dialogs*", "/friendship/accept*", "/friendship/remove*", "/account/delete*",
                "/account/update*", "/account/savexml*", "account/upload*");
        registry.addInterceptor(context.getBean(GroupModerOwnerChecker.class)).addPathPatterns("/group/moderators/*",
                "/group/requests*", "/group/accept*", "/group/delete*", "/group/update*");
        registry.addInterceptor(context.getBean(GroupMemberModerOwnerChecker.class)).addPathPatterns("/group/members*",
                "/group/leave*");
    }
}
