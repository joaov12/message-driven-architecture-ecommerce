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

    // Estes valores devem corresponder ao que o Microsserviço #1 está usando
    @Value("${pedido.exchange.name}") // Ex: pedidos.v1.events
    private String exchangeName;

    @Value("${pedido.routingkey.pedido-criado}") // Ex: pedido.criado
    private String routingKeyPedidoCriado;

    // Nome da fila para este consumidor (Microsserviço #2)
    @Value("${entrega.queue.name}") // Ex: entregas.v1.pedidos-criados.queue
    private String queueName;

    @Bean
    public TopicExchange pedidosExchange() {
        // Apenas declaramos para garantir que o Spring saiba sobre ela,
        // mas o produtor (MS1) é quem efetivamente a cria se não existir.
        // O importante é que o nome seja o mesmo.
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue entregasQueue() {
        // durable: true (a fila sobrevive a restarts do broker)
        return new Queue(queueName, true);
    }

    @Bean
    public Binding bindingPedidosCriados(Queue entregasQueue, TopicExchange pedidosExchange) {
        return BindingBuilder.bind(entregasQueue)
                .to(pedidosExchange)
                .with(routingKeyPedidoCriado); // Escuta mensagens com esta routing key
    }

    // Essencial para que o RabbitMQ consiga desserializar a mensagem JSON para o nosso DTO
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
