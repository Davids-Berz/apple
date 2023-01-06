package com.apple.apple;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apple.apple.models.IService;
import com.apple.apple.models.IndexService;

@Configuration
public class AppConfig {
    
    @Bean
    @Qualifier("IndexService")
    IService inyectarConenedor() {
        return new IndexService();
    }
}
