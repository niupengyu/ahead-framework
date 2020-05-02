/**
 * 文件名: DaoConfig.java
 * 包路径: cn.newsframework.sys.Initializer
 * 创建描述
 *        @createPerson：牛鹏宇
 *        @createDate：2016-5-19 下午1:03:55
 *        内容描述：
 * 修改描述
 *        @updatePerson：牛鹏宇
 *        @updateDate：2016-5-19 下午1:03:55
 *        修改内容:
 * 版本: V1.0
 */
package com.github.niupengyu.jdbc.config.simple;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.core.util.Content;
import com.github.niupengyu.core.util.StringUtil;
import com.github.niupengyu.jdbc.bean.DataSourceBean;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.github.niupengyu.jdbc.dao.JdbcDao;
import com.github.niupengyu.jdbc.datasource.BasicDataSource;
import com.github.niupengyu.jdbc.datasource.DBContextHolder;
import com.github.niupengyu.jdbc.datasource.DataSourceManager;
import com.github.niupengyu.jdbc.datasource.DynamicDataSource;
import com.github.niupengyu.jdbc.util.BeanFactory;
import com.github.niupengyu.jdbc.util.DataSourceFactory;
import com.github.niupengyu.jdbc.util.ResourcesUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@AutoConfig(name = "news.defaultJdbc.enable")
//@AutoConfigureBefore(MapperScanner.class)
//@AutoConfigureOrder(1)
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
//@EnableTransactionManagement
public class DaoConfig implements TransactionManagementConfigurer{

	@Resource(name="db")
	private DbConfig db;

	private static final Logger logger = LoggerFactory.getLogger(DaoConfig.class);

	public DaoConfig(){

	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}


	@Bean(name = "multipleDataSource")
	public DynamicDataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		try {
			List<DataSourceBean> dataSources=db.getDataSources();
			if(dataSources!=null){
				logger.info("datasources size "+dataSources.size());
				DataSourceBean dataSource0=dataSources.get(0);
				String beanName0=dataSource0.getName();
				DataSource basicDataSource=basicDataSource(dataSource0);

				targetDataSources.put(beanName0,basicDataSource);
				dynamicDataSource.setDefaultTargetDataSource(basicDataSource);
				DataSourceManager.put(beanName0+"DataSource",basicDataSource);
				DBContextHolder.setDbType(beanName0);
				for (int i=1;i<dataSources.size();i++) {
					DataSourceBean dataSource=dataSources.get(i);
					Boolean pool=dataSource.getPool();
					if(pool!=null&&pool==false){
						continue;
					}
					String beanName=dataSource.getName();
					DataSource basicDataSource1=basicDataSource(dataSource);
					DataSourceManager.put(beanName+"DataSource",basicDataSource1);
					targetDataSources.put(beanName,basicDataSource1);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		dynamicDataSource.setTargetDataSources(targetDataSources);
		return dynamicDataSource;
	}

	@Bean(name = "mbconfigurationy")
	@ConditionalOnBean(name = "multipleDataSource")
	public org.apache.ibatis.session.Configuration configuration(){
		org.apache.ibatis.session.Configuration configuration=
				new org.apache.ibatis.session.Configuration();
		Boolean callSettersOnNulls=StringUtil.booleanValueOf(db.getCallSettersOnNulls(),true);

		Boolean useColumnLabel=StringUtil.booleanValueOf(db.getUseColumnLabel(),true);

		Boolean mapUnderscoreToCamelCase=StringUtil.booleanValueOf(db.getMapUnderscoreToCamelCase(),false);
		configuration.setCallSettersOnNulls(callSettersOnNulls);
		configuration.setUseColumnLabel(useColumnLabel);
		configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
		List<String> mappers=db.getMappers();
		if(mappers!=null){
			for(String mapper:mappers){
				configuration.addMappers(mapper);
			}

		}
		return configuration;
	}

	@Bean(name = "mbsessionFactory")
	@ConditionalOnBean(name = "multipleDataSource")
	public SqlSessionFactory mbSessionFactoryBean(){
		try {
			SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
			sessionFactory.setDataSource(dynamicDataSource());
			String[] xmlpaths=db.getMappersXml();
			String xmlpath=db.getXmlBase();
			ResourcesUtil util=new ResourcesUtil();
			org.springframework.core.io.Resource[] resources=util.resources(xmlpaths,xmlpath);
			sessionFactory.setMapperLocations(resources);
			sessionFactory.setConfiguration(configuration());
			return sessionFactory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Bean("sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		SqlSessionTemplate sqlSessionTemplate=new SqlSessionTemplate(mbSessionFactoryBean());
		return sqlSessionTemplate;
	}


	@Bean("jdbcDao")
	@ConditionalOnBean(name = "multipleDataSource")
	public JdbcDao jdbcDao(){
		JdbcDao jdbcDao=new JdbcDao();
		jdbcDao.setDataSource(dynamicDataSource());
		return jdbcDao;
	}



	@Bean
	@ConditionalOnBean(name = "multipleDataSource")
	public PlatformTransactionManager txManager() {

//		UserTransactionManager userTransactionManager = new UserTransactionManager();
//		UserTransaction userTransaction = new UserTransactionImp();
//		return new JtaTransactionManager(userTransaction, userTransactionManager);
		return new DataSourceTransactionManager(dynamicDataSource());
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager();
	}

//	@Bean
//	public MapperScannerConfigurer mapperScannerConfigurer(){
//		MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
//		mapperScannerConfigurer.setBasePackage("news.mocroservices.*.mapper");
//		mapperScannerConfigurer.setSqlSessionFactoryBeanName("mbsessionFactory");
//		return mapperScannerConfigurer;
//	}


	public DataSource basicDataSource(DataSourceBean dataSource){

		return DataSourceFactory.basicDataSource(dataSource,db);
	}


	public DbConfig getDb() {
		return db;
	}

	public void setDb(DbConfig db) {
		this.db = db;
	}

	public static void main(String[] args) {
		StringBuilder sb=new StringBuilder();
		if(sb.length()>0){
			sb.deleteCharAt(0);
		}
	}


}
