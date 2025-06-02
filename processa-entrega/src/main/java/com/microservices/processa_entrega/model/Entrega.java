package com.microservices.processa_entrega.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_entregas")
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;
    private String enderecoEntrega;
    private String status;

    public Entrega() {
    }

    public Entrega(Long pedidoId, String enderecoEntrega, String status) {
        this.pedidoId = pedidoId;
        this.enderecoEntrega = enderecoEntrega;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Entrega{" +
                "id=" + id +
                ", pedidoId=" + pedidoId +
                ", enderecoEntrega='" + enderecoEntrega + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
