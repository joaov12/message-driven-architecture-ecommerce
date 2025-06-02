package com.microservices.processa_pedido.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;


@Configuration
public class RabbitMQConfig {

    @Value("${pedido.exchange.name}")
    private String exchangeName;

    @Bean
    public Exchange pedidosExchange(){
        return new TopicExchange(exchangeName);
    }

    // Configura o Spring para converter objetos para JSON ao enviar para o RabbitMQ
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
