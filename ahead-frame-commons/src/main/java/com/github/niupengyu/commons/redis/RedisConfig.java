package com.github.niupengyu.commons.redis;

import com.github.niupengyu.core.annotation.AutoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@AutoConfig(name = "news.redis.enable")
public class RedisConfig{

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${redis.host}")
  private String host;

  @Value("${redis.port}")
  private int port;

  @Value("${redis.timeout}")
  private int timeout;

  @Value("${redis.password}")
  private String password;

  /**
   * redis模板，存储关键字是字符串，值是Jdk序列化
   * @Description:
   * @param factory
   * @return
   */
  @Bean
  public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
      RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(factory);
      RedisSerializer<String> redisSerializer = new StringRedisSerializer();
      redisTemplate.setKeySerializer(redisSerializer);
      redisTemplate.setHashKeySerializer(redisSerializer);
      //JdkSerializationRedisSerializer序列化方式;
      JdkSerializationRedisSerializer jdkRedisSerializer=new JdkSerializationRedisSerializer();
      redisTemplate.setValueSerializer(jdkRedisSerializer);
      redisTemplate.setHashValueSerializer(jdkRedisSerializer);
      redisTemplate.afterPropertiesSet();
      return redisTemplate; 
  }
}
