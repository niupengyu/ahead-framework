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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


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

	private final static String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

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
		WebRequestInterceptorImpl webRequestInterceptor=
				new WebRequestInterceptorImpl();
		//webRequestInterceptor
		return webRequestInterceptor;
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
		converter.setObjectMapper(objectMapper());
		return converter;
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		Iterator<HttpMessageConverter<?>> it = converters.iterator();
		while(it.hasNext()){
			HttpMessageConverter<?> messageConverter = it.next();
			if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
				//it.remove();
				((MappingJackson2HttpMessageConverter) messageConverter).setObjectMapper(objectMapper());
			}
		}
		//converters.add(messageConverter());
	}

	@Bean
	public ObjectMapper objectMapper() {
		logger.info("配置 json转换类");
		ObjectMapper objectMapper= new Jackson2ObjectMapperBuilder()
				.findModulesViaServiceLoader(true)
				.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(
						DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)))
				.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(
						DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)))
				/*.serializerByType(Timestamp.class, new LocalDateTimeSerializer(
						DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)))
				.deserializerByType(Timestamp.class, new LocalDateTimeDeserializer(
						DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)))*/

				.build();
		SimpleDateFormat myDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(myDateFormat);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		return objectMapper;
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


