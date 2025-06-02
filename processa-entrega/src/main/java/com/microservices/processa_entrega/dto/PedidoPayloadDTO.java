package com.microservices.processa_entrega.dto;


public class PedidoPayloadDTO {
    private Long id;
    private String descricao;
    private Double valorTotal;
    private String status;
    private String localEntrega;

    public PedidoPayloadDTO() {
    }

    public PedidoPayloadDTO(Long id, String descricao, Double valorTotal, String status, String localEntrega) {
        this.id = id;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.status = status;
        this.localEntrega = localEntrega;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getLocalEntrega() {
        return localEntrega;
    }

    public void setLocalEntrega(String localEntrega) {
        this.localEntrega = localEntrega;
    }
}
