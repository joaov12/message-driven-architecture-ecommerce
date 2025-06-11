package com.microservices.processa_entrega.dto;

public class EntregaNotificacaoPayloadDTO {
    private Long entregaId;
    private Long pedidoId;
    private String statusEntrega;
    private String emailCliente;
    private String descricao;
    private String mensagem = "Sua entrega foi processada, enviada para a transportadora e está a caminho!";

    public EntregaNotificacaoPayloadDTO() {
    }

    public EntregaNotificacaoPayloadDTO(Long entregaId, Long pedidoId, String statusEntrega, String mensagem, String emailCliente, String descricao) {
        this.entregaId = entregaId;
        this.pedidoId = pedidoId;
        this.statusEntrega = statusEntrega;
        this.mensagem = mensagem;
        this.emailCliente = emailCliente;
        this.descricao = descricao;
    }

    public Long getEntregaId() {
        return entregaId;
    }

    public void setDescricao(String descricao) {
        this.descricao= descricao;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setEntregaId(Long entregaId) {
        this.entregaId = entregaId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getEmailCliente() {
        return emailCliente;
    }
    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }


    public String getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(String statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "EntregaNotificaçãoPayloadDTO{" +
                "entregaId=" + entregaId +
                ", pedidoId=" + pedidoId +
                ", statusEntrega='" + statusEntrega + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
