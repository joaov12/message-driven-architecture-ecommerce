package com.microservices.processa_pedido.controller;

import com.microservices.processa_pedido.model.Pedido;
import com.microservices.processa_pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.criarPedido(pedido);
        return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
    }
}
