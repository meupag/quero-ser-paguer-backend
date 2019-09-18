package com.ordersapi.orders.Domain.Services;

import com.ordersapi.orders.Domain.Entities.Cliente;
import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Entities.PedidoItem;
import com.ordersapi.orders.Domain.Entities.Produto;
import com.ordersapi.orders.Domain.Repositories.IClientService;
import com.ordersapi.orders.Domain.Repositories.IOrderItemRepository;
import com.ordersapi.orders.Domain.Repositories.IOrderRepository;
import com.ordersapi.orders.Domain.Repositories.IProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDomainService {
    private IOrderRepository _repository;
    private IClientService _service;
    private IOrderItemRepository _orderItemRepository;
    private IProductRepository _productRepository;

    public OrderDomainService(
            IOrderRepository repository,
            IClientService clientService,
            IOrderItemRepository orderItemRepository,
            IProductRepository productRepository
    )
    {
        _repository = repository;
        _service = clientService;
        _orderItemRepository = orderItemRepository;
        _productRepository = productRepository;
    }

    public List<Pedido> listOrders(){
        List<Pedido> listOrdersFromDataBase =  _repository.findAll();
        List<Pedido> listOrders = new ArrayList<Pedido>();
        for(Pedido pedido : listOrdersFromDataBase){
           List<PedidoItem> itemList = _orderItemRepository.findByPedidoId(pedido.getId());
           pedido.setValor(calculaValorDoPedido(itemList));
           pedido.setPedidoItens(itemList);
           listOrders.add(pedido);
        }
        return listOrders;
    }

    public Pedido createOrder(Pedido order) throws Exception {
        Cliente client = _service.getClientById(order.getClienteId());
        if(client != null && client.Id != null){
            return (Pedido) _repository.save(order);
        }else{
            throw new Exception("Cliente não existe na base");
        }
    }

    public Pedido getById(UUID PedidoId){
        Pedido pedido = (Pedido) _repository.findAllById(PedidoId);
        List<PedidoItem> itemList = _orderItemRepository.findByPedidoId(pedido.getId());
        pedido.setValor(calculaValorDoPedido(itemList));
        pedido.setPedidoItens(itemList);
        return pedido;
    };

    public PedidoItem addOrderItem (PedidoItem pedidoItem){
        Produto produto = (Produto) _productRepository.findAllById(pedidoItem.getProdutoId());
        pedidoItem.setPreco((produto.getPrecoSugerido() * pedidoItem.getQuantidade()));
        return (PedidoItem) _orderItemRepository.save(pedidoItem);
    };

    public PedidoItem updateOrderItem(PedidoItem pedidoItem){
        PedidoItem pedidoItemSave = (PedidoItem) _orderItemRepository.findAllById(pedidoItem.getId());
        Produto produto = (Produto) _productRepository.findAllById(pedidoItem.getProdutoId());
        pedidoItemSave.setPreco((produto.getPrecoSugerido() * pedidoItem.getQuantidade()));
        pedidoItemSave.setQuantidade(pedidoItem.getQuantidade());
        return (PedidoItem) _orderItemRepository.save(pedidoItemSave);
    }

    public void removeOrderItem(UUID id) throws Exception {
        PedidoItem pedidoItemSave = (PedidoItem) _orderItemRepository.findAllById(id);
        if(pedidoItemSave != null){
            _orderItemRepository.deleteById(id);
        }else {
            throw new Exception("Item do pedido não existe na base ");
        }
    };

    public List<Pedido> getOrdersByClienteId(UUID ClienteId){
        List<Pedido> listOrdersFromDataBase =  _repository.findByClienteId(ClienteId);
        List<Pedido> listOrders = new ArrayList<Pedido>();
        for(Pedido pedido : listOrdersFromDataBase){
            List<PedidoItem> itemList = _orderItemRepository.findByPedidoId(pedido.getId());
            pedido.setValor(calculaValorDoPedido(itemList));
            pedido.setPedidoItens(itemList);
            listOrders.add(pedido);
        }
        return listOrders;
    };

    private double  calculaValorDoPedido(List<PedidoItem> pedidoItems){
        return pedidoItems.stream().mapToDouble(x -> x.getPreco()).sum();
    };


}
