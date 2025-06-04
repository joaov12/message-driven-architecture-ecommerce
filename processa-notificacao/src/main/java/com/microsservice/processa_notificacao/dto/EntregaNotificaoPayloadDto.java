package com.microsservice.processa_notificacao.dto;

public class EntregaNotificaoPayloadDto {
    private Long entregaId;
    private Long pedidoId;
    private String statusEntrega;
    private String mensagem;
    private String emailCliente;

    public EntregaNotificaoPayloadDto() {
    }

    public EntregaNotificaoPayloadDto(Long entregaId, Long pedidoId, String statusEntrega, String mensagem, String emailCliente) {
        this.entregaId = entregaId;
        this.pedidoId = pedidoId;
        this.statusEntrega = statusEntrega;
        this.mensagem = mensagem;
        this.emailCliente = emailCliente;
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

    public String getmensagem() {
        return mensagem;
    }

    public void setmensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    @Override
    public String toString() {
        return "EntregaNotificaoPayloadDto{" +
                "entregaId=" + entregaId +
                ", pedidoId=" + pedidoId +
                ", statusEntrega='" + statusEntrega + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                '}';
    }

}
