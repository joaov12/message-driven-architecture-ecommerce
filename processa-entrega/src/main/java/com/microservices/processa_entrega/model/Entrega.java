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
    private String emailCliente;
    private String descricao;

    public Entrega() {
    }

    public Entrega(Long pedidoId, String enderecoEntrega, String status, String emailCliente, String descricao) {
        this.pedidoId = pedidoId;
        this.enderecoEntrega = enderecoEntrega;
        this.status = status;
        this.emailCliente = emailCliente;
        this.descricao = descricao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }


    @Override
    public String toString() {
        return "Entrega{" +
                "id=" + id +
                ", pedidoId=" + pedidoId +
                ", enderecoEntrega='" + enderecoEntrega + '\'' +
                ", status='" + status + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
