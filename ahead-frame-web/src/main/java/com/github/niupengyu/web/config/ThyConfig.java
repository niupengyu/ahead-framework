package com.github.niupengyu.web.config;

import com.github.niupengyu.core.annotation.AutoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@AutoConfig(name="news.views.thy")
public class ThyConfig {

    private static final Logger logger= LoggerFactory.getLogger(ThyConfig.class);

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        logger.info("init thy config");
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setCharacterEncoding("utf-8");
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine());
        thymeleafViewResolver.setViewNames(new String[]{"templates/*"});
        thymeleafViewResolver.setOrder(2);
        return thymeleafViewResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(springResourceTemplateResolver());
        return engine;
    }

    @Bean
    public ITemplateResolver springResourceTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/pages/");
        resolver.setTemplateMode("HTML5");
        resolver.setSuffix(".html");
        resolver.setCacheable(false);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

}
