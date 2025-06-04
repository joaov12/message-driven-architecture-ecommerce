package com.microsservice.processa_notificacao.service; // Seu pacote

import com.microsservice.processa_notificacao.dto.EntregaNotificaoPayloadDto; // Renomeie se necessário
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value; // Não mais necessário para o ARN do tópico de e-mail
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificacaoService {
    private static final Logger log = LoggerFactory.getLogger(NotificacaoService.class);

    @Autowired
    private JavaMailSender mailSender; // Injeta o JavaMailSender do Spring

    @Value("${spring.mail.username}")
    private String fromEmail;



    public void enviarNotificacaoPorEmail(EntregaNotificaoPayloadDto payload) {
        String email = payload.getEmailCliente();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);


            String subject = String.format("Atualização do Pedido %d - Entrega %d",
                    payload.getPedidoId(), payload.getEntregaId());
            message.setSubject(subject);

            String messageBodyText = String.format(
                    "Olá!\n\n O seu pedido foi processado e já está com a transportadora\n\n" +
                            "- - - DEBUG - - - \n" +
                            "Pedido ID: %d\n" +
                            "Entrega ID: %d\n",
                    payload.getPedidoId(),
                    payload.getEntregaId(),

                    payload.getmensagem() != null ? "com a mensagem '"+payload.getmensagem()+"'" : "",
                    payload.getStatusEntrega(),
                    payload.getPedidoId(),
                    payload.getEntregaId(),
                    payload.getmensagem() != null ? payload.getmensagem() : "N/A"
            );
            message.setText(messageBodyText);

            mailSender.send(message);
            log.info("Notificação por e-mail enviada via Gmail SMTP para {} sobre a entrega {}", email, payload.getEntregaId());

        } catch (MailException e) {
            log.error("Erro ao enviar e-mail via Gmail SMTP para {}: {}", email, e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao enviar notificação por e-mail: {}", e.getMessage(), e);
        }
    }
}