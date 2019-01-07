package com.example.common.hello.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.camel.component.ActiveMQConfiguration;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JMSConfig {

  @Value("${activemq.broker.url}")
  private String brokerUrl;

  @Value("${activemq.broker.username}")
  private String userName;

  @Value("${activemq.broker.password}")
  private String password;

  @Value("${activemq.concurrent.consumers}")
  private int concurrentConsumers;

  @Value("${activemq.pool.max.connections}")
  private int maxConnections;

  @Bean
  public ActiveMQConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(brokerUrl);
    connectionFactory.setUserName(userName);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }

  @Bean
  public PooledConnectionFactory pooledConnectionFactory() {
    PooledConnectionFactory factory = new PooledConnectionFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setMaxConnections(maxConnections);
    return factory;
  }

  @Bean
  public ActiveMQConfiguration config() {
    ActiveMQConfiguration config = new ActiveMQConfiguration();
    config.setConnectionFactory(pooledConnectionFactory());
    config.setConcurrentConsumers(concurrentConsumers);
    return config;
  }

  @Bean
  public ActiveMQComponent amq() {
    ActiveMQComponent component = new ActiveMQComponent();
    component.setConfiguration(config());
    return component;
  }

}
