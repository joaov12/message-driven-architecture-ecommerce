package com.microservices.processa_entrega.listener;

import com.microservices.processa_entrega.dto.PedidoPayloadDTO;
import com.microservices.processa_entrega.service.EntregaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoListener.class);

    @Autowired
    EntregaService entregaService;

    @RabbitListener(queues = "${entrega.queue.name}")
    public void receberPedidoParaEntrega(@Payload PedidoPayloadDTO pedidoPayload) {
        log.info("Mensagem recebida da fila {}: {}", "${entrega.queue.name}", pedidoPayload);
        try {
            entregaService.processarPedidoParaEntrega(pedidoPayload);
            log.info("Pedido {} processado para entrega com sucesso.", pedidoPayload.getId());
        } catch (Exception e) {
            log.error("Erro ao processar pedido {} para entrega: {}", pedidoPayload.getId(), e.getMessage());
        }
    }
}
