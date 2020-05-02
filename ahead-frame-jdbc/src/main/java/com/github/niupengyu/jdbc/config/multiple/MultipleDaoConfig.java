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
package com.github.niupengyu.jdbc.config.multiple;

import com.github.niupengyu.core.annotation.AutoConfig;
import com.github.niupengyu.jdbc.bean.DbConfig;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;


@Configuration()
@AutoConfig(name = "news.multipleJdbc.enable")
@Import(MultipleJdbcBean.class)
public class MultipleDaoConfig{

	private static final Logger logger = LoggerFactory.getLogger(MultipleDaoConfig.class);

	@Resource(name="db")
	private DbConfig db;

	@Bean
	public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}


	@Bean(name = "userTransaction")
	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		//userTransactionImp.setTransactionTimeout(10000);
		return userTransactionImp;
	}

	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
	public TransactionManager atomikosTransactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		return userTransactionManager;
	}

	@Bean(name = "transactionManager")
	@DependsOn({ "userTransaction", "atomikosTransactionManager" })
	public PlatformTransactionManager transactionManager() throws Throwable {
		return new JtaTransactionManager(userTransaction(),atomikosTransactionManager());
	}

}
