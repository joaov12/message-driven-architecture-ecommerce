package com.microservices.processa_pedido.service;

import com.microservices.processa_pedido.model.Pedido;
import com.microservices.processa_pedido.repository.PedidoRepository;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {
    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${pedido.exchange.name}")
    private String exchangeName;

    @Value("${pedido.routingkey.pedido-criado}")
    private String routingKeyPedidoCriado;

    public Pedido criarPedido(Pedido pedido) {
        pedido.setStatus("RECEBIDO");
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        log.info("Pedido salvo: {}", pedidoSalvo);

        // O objeto pedidoSalvo será convertido para JSON automaticamente devido ao Jackson2JsonMessageConverter configurado
        rabbitTemplate.convertAndSend(exchangeName, routingKeyPedidoCriado, pedidoSalvo);
        log.info("Evento de pedido criado enviado para RabbitMQ: {}", pedidoSalvo);

        return pedidoSalvo;
    }

}
