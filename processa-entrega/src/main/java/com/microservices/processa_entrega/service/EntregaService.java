package com.microservices.processa_entrega.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.processa_entrega.dto.EntregaNotificacaoPayloadDTO;
import com.microservices.processa_entrega.dto.PedidoPayloadDTO;
import com.microservices.processa_entrega.model.Entrega;
import com.microservices.processa_entrega.repository.EntregaRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

@Service
public class EntregaService {
    private static final Logger log = LoggerFactory.getLogger(EntregaService.class);

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private SqsClient sqsClient; // Injete o cliente SQS

    @Autowired
    private ObjectMapper objectMapper; // Para converter o DTO para JSON

    @Value("${aws.sqs.queue.entrega-notificacao.url}")
    private String entregaNotificacaoQueueUrl;

    public void processarPedidoParaEntrega(PedidoPayloadDTO pedidoPayload) {
        log.info("Recebido pedido para processamento de entrega: {}", pedidoPayload.getId());

        String endereco = pedidoPayload.getLocalEntrega();

        Entrega novaEntrega = new Entrega(
                pedidoPayload.getId(),
                endereco,
                "ENTREGA_PROCESSADA_COM_SUCESSO", // Status inicial
                pedidoPayload.getEmailCliente(),
                pedidoPayload.getDescricao()
        );

        Entrega entregaSalva = entregaRepository.save(novaEntrega);
        log.info("Entrega registrada: {}", entregaSalva);

        entregaRepository.save(novaEntrega);
        log.info("Entrega registrada para o pedido {}: {}", pedidoPayload.getId(), novaEntrega);

        enviarNotificacaoEntregaSQS(entregaSalva);
    }

    private void enviarNotificacaoEntregaSQS(Entrega entrega) {
        try {
            EntregaNotificacaoPayloadDTO payload = new EntregaNotificacaoPayloadDTO(
                    entrega.getId(),
                    entrega.getPedidoId(),
                    entrega.getStatus(),
                    "Status da entrega atualizado para: " + entrega.getStatus(),
                    entrega.getEmailCliente(),
                    entrega.getDescricao()
            );

            String mensagemJson = objectMapper.writeValueAsString(payload);

            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(entregaNotificacaoQueueUrl)
                    .messageBody(mensagemJson)
                    .build();

            sqsClient.sendMessage(sendMessageRequest);
            log.info("Mensagem de status da entrega enviada para SQS: {}", mensagemJson);

        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar payload da entrega para JSON: {}", e.getMessage());
            // Adicionar tratamento de erro mais robusto se necessário
        } catch (SqsException e) {
            log.error("Erro ao enviar mensagem para SQS: {}", e.awsErrorDetails().errorMessage());
            // Adicionar tratamento de erro mais robusto se necessário
        }
    }
}
