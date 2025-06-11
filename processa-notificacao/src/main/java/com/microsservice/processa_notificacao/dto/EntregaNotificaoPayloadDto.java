package com.microsservice.processa_notificacao.dto;

public class EntregaNotificaoPayloadDto {
    private Long entregaId;
    private Long pedidoId;
    private String statusEntrega;
    private String mensagem;
    private String emailCliente;
    private String descricao;

    public EntregaNotificaoPayloadDto() {
    }

    public EntregaNotificaoPayloadDto(Long entregaId, Long pedidoId, String statusEntrega, String mensagem, String emailCliente, String descricao) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "EntregaNotificaoPayloadDto{" +
                "entregaId=" + entregaId +
                ", pedidoId=" + pedidoId +
                ", statusEntrega='" + statusEntrega + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

}
