package com.microsservice.processa_notificacao.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsservice.processa_notificacao.dto.EntregaNotificaoPayloadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.List;

@Component
public class SqsMessagePoller {
    private static final Logger log = LoggerFactory.getLogger(SqsMessagePoller.class);

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificacaoService notificacaoService;

    @Value("${aws.sqs.queue.entrega-notificacao.url}")
    private String queueUrl;

    // Valores padrão ou configuráveis via application.properties
    @Value("${aws.sqs.listener.max-messages:10}")
    private int maxMessages;

    @Value("${aws.sqs.listener.wait-time-seconds:20}")
    private int waitTimeSeconds; // Habilita long polling

    // Executa a cada 5 segundos (5000 ms). Ajuste conforme necessidade.
    // fixedDelayString é uma forma de usar valor de application.properties
    // @Scheduled(fixedDelayString = "${aws.sqs.listener.polling-interval-ms:5000}")
    @Scheduled(fixedDelay = 30000) // Ou um valor fixo
    public void pollMessages() {
        log.debug("Verificando mensagens na fila SQS: {}", queueUrl);
        try {
            ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(maxMessages) // Pega até X mensagens por vez
                    .waitTimeSeconds(waitTimeSeconds)   // Long polling: espera até Y segundos por mensagens
                    .build();

            List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

            if (messages.isEmpty()) {
                log.trace("Nenhuma mensagem recebida da fila SQS.");
                return;
            }

            log.info("{} mensagem(ns) recebida(s) da fila SQS.", messages.size());

            for (Message message : messages) {
                try {
                    log.debug("Processando mensagem ID: {}", message.messageId());
                    String messageBody = message.body();
                    EntregaNotificaoPayloadDto payload = objectMapper.readValue(messageBody, EntregaNotificaoPayloadDto.class);

                    notificacaoService.enviarNotificacaoPorEmail(payload); // Processa e envia para SNS

                    // Se o processamento foi bem-sucedido, deleta a mensagem da fila
                    deleteMessage(message.receiptHandle());
                    log.info("Mensagem {} processada e deletada com sucesso.", message.messageId());

                } catch (JsonProcessingException e) {
                    log.error("Erro ao desserializar mensagem SQS ID {}: {}. Conteúdo: {}", message.messageId(), e.getMessage(), message.body());
                    // Decidir o que fazer: não deletar para DLQ, logar, etc.
                    // Por enquanto, não deletamos, a mensagem ficará visível novamente após o timeout.
                } catch (Exception e) {
                    log.error("Erro ao processar mensagem SQS ID {}: {}", message.messageId(), e.getMessage(), e);
                    // Não deletar em caso de erro de processamento para permitir nova tentativa ou DLQ.
                }
            }
        } catch (SqsException e) {
            log.error("Erro ao tentar receber mensagens do SQS: {}", e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            log.error("Erro inesperado no polling SQS: {}", e.getMessage(), e);
        }
    }

    private void deleteMessage(String receiptHandle) {
        try {
            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(receiptHandle)
                    .build();
            sqsClient.deleteMessage(deleteRequest);
        } catch (SqsException e) {
            log.error("Erro ao deletar mensagem do SQS com receiptHandle {}: {}", receiptHandle, e.awsErrorDetails().errorMessage());
        }
    }
}
