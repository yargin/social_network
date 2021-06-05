package com.getjavajob.training.yarginy.socialnetwork.common.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.getjavajob.training.yarginy.socialnetwork.common")
@EntityScan("com.getjavajob.training.yarginy.socialnetwork.common.models")
public class CommonConfiguration {
}
