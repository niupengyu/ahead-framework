package com.github.niupengyu.core.factory;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.core.annotation.ScheduleService;
import com.github.niupengyu.core.bean.FactoryConfig;
import com.github.niupengyu.core.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

//import org.springframework.boot.bind.RelaxedPropertyResolver;

@Configuration
@AutoConfig(name = "news.bean-factory.enable")
public class AheadBeanFactory
        implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {


    private Logger logger= LoggerFactory.getLogger(AheadBeanFactory.class);

    private FactoryConfig factoryConfig;

    @Override
    public void setEnvironment(Environment environment) {
        Binder binder = Binder.get(environment);
        BindResult<FactoryConfig> bindResult=binder.bind("news.bean-factory", Bindable.of(FactoryConfig.class));
        if(bindResult.isBound()){
            factoryConfig = bindResult.get();
        }

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String[] packs=factoryConfig.getClassPackages();
        System.out.println(Arrays.toString(packs));
        for(String pack:packs){
            System.out.println(pack);
            Set<Class<?>> classSet = ClassUtil.getClasses(pack);
            System.out.println(classSet);
            for(Class<?> set:classSet){
                System.out.println(set);
                String name=set.getName();
                System.out.println(name);
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                        .genericBeanDefinition(set);
                Service service=set.getAnnotation(Service.class);
                Repository repository=set.getAnnotation(Repository.class);
                Component component=set.getAnnotation(Component.class);

                if(service!=null){
                    beanDefinitionRegistry.registerBeanDefinition(service.value(),beanDefinitionBuilder.getRawBeanDefinition());
                    logger.info("registry service class "+name);
                }if(component!=null){
                    beanDefinitionRegistry.registerBeanDefinition(component.value(),beanDefinitionBuilder.getRawBeanDefinition());
                    logger.info("registry component class "+name);
                }if(repository!=null){
                    beanDefinitionRegistry.registerBeanDefinition(repository.value(),beanDefinitionBuilder.getRawBeanDefinition());
                    logger.info("registry repository class "+name);
                }else{
                    ScheduleService scheduleService=set.getAnnotation(ScheduleService.class);
                    if(scheduleService!=null&&scheduleService.registry()){
                        logger.info("registry class "+name);
                        beanDefinitionRegistry.registerBeanDefinition(scheduleService.value(),beanDefinitionBuilder.getRawBeanDefinition());
                    }else{
                        logger.info("skip class "+name);
                    }
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        logger.info("postProcessBeanFactory...");
    }



    /*@Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        String pack=factoryConfig.getClassPackage();
        Set<Class<?>> classSet = ClassUtil.getClasses(pack);
        for(Class<?> set:classSet){
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                    .genericBeanDefinition(set);
            Service service=set.getAnnotation(Service.class);
            if(service!=null){
                beanDefinitionRegistry.registerBeanDefinition(service.value(),beanDefinitionBuilder.getRawBeanDefinition());
            }else{
                beanDefinitionRegistry.registerBeanDefinition(set.getName(),beanDefinitionBuilder.getRawBeanDefinition());
            }
        }
    }*/



}
