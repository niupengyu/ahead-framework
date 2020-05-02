package com.github.niupengyu.socket.client.config;


import com.github.niupengyu.socket.client.service.ClientHandler;
import com.github.niupengyu.socket.code.ByteArrayCodecFactory;
import com.github.niupengyu.socket.code.MyKeepAliveMessageFactory;
import com.github.niupengyu.socket.code.MyKeepAliveRequestTimeoutHandlerImpl;
import com.github.niupengyu.socket.handler.ClientService;
import com.github.niupengyu.socket.handler.KeepAliveService;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
*Description: <应用配置类>. <br>
*<p>
	<负责注册除Controller等web层以外的所有bean，包括aop代理，service层，dao层，缓存，等等>
 </p>
*Makedate:2014年9月3日 上午9:58:15
* @author Administrator
* @version V1.0
*/
//@Configuration
//@AutoConfig(name="news.slave.enable")
public class ClientSocketConfig {

	private static final Logger logger = LoggerFactory.getLogger(ClientSocketConfig.class);

	private KeepAliveService keepAliveService;

	//private ClientService clientService;

	//@Bean("slaveFilterChainBuilder")
	public DefaultIoFilterChainBuilder filterChainBuilder() {
		DefaultIoFilterChainBuilder defaultIoFilterChainBuilder =
				new DefaultIoFilterChainBuilder();
		//mina自带的线程池filter
		defaultIoFilterChainBuilder.addLast("executor", executorFilter());
		defaultIoFilterChainBuilder.addLast("mdcInjectionFilter", mdcInjectionFilter());
		//日志的filter
		defaultIoFilterChainBuilder.addLast("loggingFilter", loggingFilter());
		//-自己实现的编解码器filter
		defaultIoFilterChainBuilder.addLast("codecFilter", protocolCodecFilter());
		//心跳filter
		defaultIoFilterChainBuilder.addLast("keepAliveFilter", keepAliveFilter());
		return defaultIoFilterChainBuilder;
	}

	// executorFilter多线程处理
	//@Bean
	public ExecutorFilter executorFilter()
	{
		return new ExecutorFilter();
	}

	//
	//@Bean
	public MdcInjectionFilter mdcInjectionFilter() {
		return new MdcInjectionFilter();
	}

	//日志
	//@Bean("loggingFilter")
	public LoggingFilter loggingFilter() {
		LoggingFilter loggingFilter=new LoggingFilter();
		loggingFilter.setSessionClosedLogLevel(LogLevel.NONE);
		loggingFilter.setSessionCreatedLogLevel(LogLevel.NONE);
		loggingFilter.setSessionOpenedLogLevel(LogLevel.NONE);
		loggingFilter.setSessionIdleLogLevel(LogLevel.NONE);
		loggingFilter.setMessageReceivedLogLevel(LogLevel.NONE);
		loggingFilter.setMessageSentLogLevel(LogLevel.NONE);
		return loggingFilter;
	}

	//编解码
	//@Bean
	public ProtocolCodecFilter protocolCodecFilter() {
		//构造函数的参数传入自己实现的对象
		//MyCodeFactory mf = new MyCodeFactory();
		//mf.setDecoderMaxLineLength(10240);
		//mf.setEncoderMaxLineLength(10240);
		ByteArrayCodecFactory mf=new ByteArrayCodecFactory();
		return new ProtocolCodecFilter(mf);
	}

	//心跳检测filter
	//@Bean
	public KeepAliveFilter keepAliveFilter() {
		//构造函数的第一个参数传入自己实现的工厂
		//第二个参数需要的是IdleStatus对象，value值设置为读写空闲
		KeepAliveFilter keepAliveFilter = new KeepAliveFilter(keepAliveMessageFactory(),
				IdleStatus.BOTH_IDLE, heartBeatHandler());
		//心跳超时时间，不设置则默认30s
		keepAliveFilter.setRequestTimeout(5);
		//心跳频率，不设置则默认60s
		keepAliveFilter.setRequestInterval(10);
		//不设置默认false
		keepAliveFilter.setForwardEvent(true);

		return keepAliveFilter;
	}

	//@Bean
	public KeepAliveMessageFactory keepAliveMessageFactory() {
		MyKeepAliveMessageFactory keepAliveMessageFactory=new MyKeepAliveMessageFactory();
		keepAliveMessageFactory.setKeepAliveService(keepAliveService());
		return keepAliveMessageFactory;
	}

	//@Bean
	public KeepAliveRequestTimeoutHandler heartBeatHandler() {
		MyKeepAliveRequestTimeoutHandlerImpl keepAliveRequestTimeoutHandler=
				new MyKeepAliveRequestTimeoutHandlerImpl();
		keepAliveRequestTimeoutHandler.setKeepAliveService(keepAliveService());
		return keepAliveRequestTimeoutHandler;
	}



	//@Bean
	public KeepAliveService keepAliveService() {
		KeepAliveService clientKeepAliveService=this.keepAliveService;
		return clientKeepAliveService;
	}

	//@Bean
	/*public ClientHandler clientHandlerService(){
		ClientHandler clientHandler=new ClientHandler();
		clientHandler.setClientService(clientService);
		return clientHandler;
	}*/

	public void setKeepAliveService(KeepAliveService keepAliveService) {
		this.keepAliveService = keepAliveService;
	}

	/*public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}*/
}