package com.apple.apple;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource("classpath:messages.properties")
})
public class MessagesPropertiesConfig {
    
}
