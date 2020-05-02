package com.github.niupengyu.web.config;


import com.github.niupengyu.core.annotation.AutoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@AutoConfig(name="news.views.jsp")
public class JspConfig {

    private static final Logger logger= LoggerFactory.getLogger(JspConfig.class);

    @Bean
    public ViewResolver internalResourceViewResolver() {
        logger.info("init jsp config");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setCache(false);
        viewResolver.setPrefix("/pages/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewNames("jsp/*");
        viewResolver.setOrder(1);
        return viewResolver;
    }

}
