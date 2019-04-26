package main.java.controllers;

import main.java.models.Pedido;
import main.java.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/pedidos")
    public @ResponseBody Iterable<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }
}