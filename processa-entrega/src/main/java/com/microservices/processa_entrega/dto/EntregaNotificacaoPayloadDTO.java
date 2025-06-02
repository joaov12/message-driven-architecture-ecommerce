package com.microservices.processa_entrega.dto;

public class EntregaNotificacaoPayloadDTO {
    private Long entregaId;
    private Long pedidoId;
    private String statusEntrega;
    private String mensagem = "Sua entrega foi processada, enviada para a transportadora e está a caminho!";

    public EntregaNotificacaoPayloadDTO() {
    }

    public EntregaNotificacaoPayloadDTO(Long entregaId, Long pedidoId, String statusEntrega, String mensagem) {
        this.entregaId = entregaId;
        this.pedidoId = pedidoId;
        this.statusEntrega = statusEntrega;
        this.mensagem = mensagem;
    }

    public Long getEntregaId() {
        return entregaId;
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
                '}';
    }
}
