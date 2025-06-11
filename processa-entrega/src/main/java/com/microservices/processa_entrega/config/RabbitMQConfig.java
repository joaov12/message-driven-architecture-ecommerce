package com.microservices.processa_entrega.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
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

    @Value("${pedido.routingkey.pedido-criado}") 
    private String routingKeyPedidoCriado;

    @Value("${entrega.queue.name}") 
    private String queueName;

    @Bean
    public TopicExchange pedidosExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue entregasQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding bindingPedidosCriados(Queue entregasQueue, TopicExchange pedidosExchange) {
        return BindingBuilder.bind(entregasQueue)
                .to(pedidosExchange)
                .with(routingKeyPedidoCriado); 
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
