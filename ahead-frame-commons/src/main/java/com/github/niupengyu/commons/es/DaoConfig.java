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
package com.github.niupengyu.commons.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;


//@Service("esConfig")
public class DaoConfig {

	private static final Logger logger = LoggerFactory.getLogger(DaoConfig.class);

	private String name="testes";

	//private String index="test";

	//private String type="test";

	private String ip="localhost";

	private Integer port=9300;

	private TransportClient transportClient = null;
//	@Bean("transportClient")
	public TransportClient elasticsearchClient() {   //向spring注入es的客户端操作对象
		if(transportClient!=null){
			return  transportClient;
		}
		System.setProperty("es.set.netty.runtime.available.processors","false");
		logger.info("链接elasticsearch服务...");

		try {
			Settings settings = Settings.builder()
					.put("cluster.name", name).build();
			transportClient = new PreBuiltTransportClient(settings);
			transportClient.addTransportAddress(
					new TransportAddress(InetAddress.getByName(ip), port)
			);
		} catch (UnknownHostException e) {
			logger.error("创建elasticsearch客户端失败");
		}
		logger.info("创建elasticsearch客户端成功");

		return transportClient;
	}

}
