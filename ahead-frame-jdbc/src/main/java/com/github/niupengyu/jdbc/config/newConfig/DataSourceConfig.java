//package com.github.niupengyu.jdbc.config.newConfig;
//
//import com.github.niupengyu.core.annotation.AutoConfig;
//import com.github.niupengyu.jdbc.util.DataSourceFactory;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.boot.context.properties.bind.BindResult;
//import org.springframework.boot.context.properties.bind.Bindable;
//import org.springframework.boot.context.properties.bind.Binder;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.env.Environment;
//import org.springframework.core.type.AnnotationMetadata;
//
//
//
//public class DataSourceConfig
//        implements ImportBeanDefinitionRegistrar, EnvironmentAware {
//
//    private JSONObject mapConfig;
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        Binder binder = Binder.get(environment);
//        BindResult<JSONObject> bindResult=binder.bind("dbcp", Bindable.of(JSONObject.class));
//        if(bindResult.isBound()){
//            mapConfig = bindResult.get();
//        }
//    }
//
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
//        //System.out.println(mapConfig);
//        try{
//            if(mapConfig==null){
//                return;
//            }
//            JSONObject dataSources=getArray(mapConfig);
//            JSONArray array=new JSONArray();
//            for(String key:dataSources.keySet()){
//                JSONObject dataSource=dataSources.getJSONObject(key);
//                array.add(Integer.parseInt(key),dataSource);
//
//                //DataSourceFactory.datSourceClass(dataSource,mapConfig);
//               // BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
//                //        .genericBeanDefinition(aa);
//            }
//            //System.out.println(array);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private JSONObject getArray(JSONObject mapConfig) {
//        JSONObject array=mapConfig.getJSONObject("dataSources");
//        return array;
//    }
//
//    /*@Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
//        System.out.println("e33333333333");
//        try{
//            JSONArray dataSources=getArray(mapConfig);
//            System.out.println("dataSources "+dataSources);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        System.out.println("222222222222222qq");
//        try{
//            JSONArray dataSources=getArray(mapConfig);
//            System.out.println("dataSources "+dataSources);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }*/
//}
