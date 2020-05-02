/**
 * 文件名: MvcConfig.java
 * 包路径: cn.newsframework.sys.Initializer
 * 创建描述
 *        @createPerson：牛鹏宇
 *        @createDate：2016-5-19 上午11:49:27
 *        内容描述：
 * 修改描述
 *        @updatePerson：牛鹏宇
 *        @updateDate：2016-5-19 上午11:49:27
 *        修改内容:
 * 版本: V1.0
 */
package com.github.niupengyu.web.config;

import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.web.beans.ResponseData;
import com.github.niupengyu.web.filter.AuthorityFilter;
import com.github.niupengyu.web.initialier.BaseInitializer;
import com.github.niupengyu.web.initialier.WebRequestInterceptorImpl;
import com.github.niupengyu.web.service.AuthorizationService;
import com.github.niupengyu.web.service.LoginValidateService;
import com.github.niupengyu.web.service.impl.AuthorizationServiceImpl;
import com.github.niupengyu.web.service.impl.LoginValidateServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.nio.charset.Charset;
import java.util.List;


@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer
	    //extends
		//DelegatingWebMvcConfiguration
		//WebMvcConfigurationSupport
{

	@Autowired
	private Environment environment;

	private static final Logger logger= LoggerFactory.getLogger(MvcConfig.class);

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {

		boolean flag= environment.getProperty("news.mvc.use-suffix-pattern-match",Boolean.class,true);
		configurer.setUseSuffixPatternMatch(flag);

	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor(){
		return new LocaleChangeInterceptor();
	}


	@Bean
	public BaseInitializer baseInitializer(){
		return new BaseInitializer();
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("开始添加拦截器:addInterceptors start");
//		InterceptorRegistration rt0 =
//				registry.addInterceptor(myInitializer());
//		rt0.addPathPatterns("/api/*/**");
		InterceptorRegistration rt =
				registry.addInterceptor(baseInitializer());
//		registry.addWebRequestInterceptor(interceptor());
		String[] exclude=new String[]{"/static/**","/favicon.ico"};
		String[] add=new String[]{"/**", "*"};
		rt.excludePathPatterns(exclude);
		rt.addPathPatterns(add);
		rt.order(1);
		InterceptorRegistration rt1 =registry.addInterceptor(interceptor());
		rt1.excludePathPatterns(exclude);
		rt1.addPathPatterns(add);
		rt1.order(2);
//		rt.addPathPatterns("/*", "*");
//		rt.addPathPatterns("/*/**", "*");
		logger.info("结束添加拦截器:addInterceptors end");
	}

	@Bean
	public HandlerInterceptorAdapter interceptor() {
		logger.info("hahahahsssssssssssss");
		WebRequestInterceptorImpl webRequestInterceptor=
				new WebRequestInterceptorImpl();
		//webRequestInterceptor
		return new WebRequestInterceptorImpl();
	}

	@ConditionalOnMissingBean(AuthorizationService.class)
	@Bean
	public AuthorizationService authorizationService(){
		return new AuthorizationServiceImpl();
	}

	@ConditionalOnMissingBean(LoginValidateService.class)
	@Bean
	public LoginValidateService loginValidateService(){
		return new LoginValidateServiceImpl();
	}


	@Bean
	public CommonsMultipartResolver commonsMultipartResolver(){
		return new CommonsMultipartResolver();
	}




	@Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

	@Bean
	public MappingJackson2HttpMessageConverter messageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		return converter;
	}

    /*@Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        //super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }*/

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }



	/**
     * 描述 : <资源访问处理器>. <br>
     *<p>
        <可以在jsp中使用/static/**的方式访问/WEB-INF/static/下的内容>
      </p>
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	logger.info("addResourceHandlers");
        registry.addResourceHandler("/css/**","/images/**","/js/**","/static/**","/favicon.ico")
        		.addResourceLocations("/css/")
        		.addResourceLocations("/images/")
        		.addResourceLocations("/js/")
        		.addResourceLocations("/static/")
        		.addResourceLocations("classpath:static/static/")
		.addResourceLocations("/favicon.ico");
    }


    @Bean
	public CommonsMultipartResolver multipartResolver(){
		logger.info("文件上传处理器:CommonsMultipartResolver");
		return new CommonsMultipartResolver();
	}


	/*@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}*/
}


