import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

import { PedidoService } from 'src/app/services/pedido.service';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css']
})
export class PedidosComponent implements OnInit {

  @ViewChild('modal')
  modal: TemplateRef<any>;
  modalRef: BsModalRef;

  produtos: any;
  clientes: any;
  pedidos: any;
  itens: any = [];

  quantidade: number = 1;

  constructor(
    private route: ActivatedRoute,
    private modalService: BsModalService,
    private pedidoService: PedidoService
  ) { }

  ngOnInit() {
    this.route.data.subscribe(
      (content) => {
        this.produtos = content.produtos;
        this.clientes = content.clientes;
        this.pedidos = content.pedidos;
      }
    );
  }

  addPedido() {
    this.modalRef = this.modalService.show(this.modal);
  }

  salvarPedido(cliente) {
    let body = {
      id_pedido: `${this.pedidos.length + 1}`,
      id_cliente: cliente,
      itens: this.itens
    }

    this.pedidoService
      .cadastrarPedido(JSON.stringify(body))
      .then((res) => {
        this.fecharModal();
        this.atualizarLista();
        this.quantidade = 1;
        this.itens = [];
      });
  }

  fecharModal() {
    this.modalRef.hide();
  }

  atualizarLista() {
    this.pedidoService
      .getListaPedidos()
      .then((data) => this.pedidos = data)
  }

  addItem() {
    ++this.quantidade;
  }

  rmItem() {
    if (this.quantidade > 1)
      --this.quantidade;
  }

  addItemPedido(produto) {
    let prod = [];
    prod = this.produtos.filter((p) => {
      return p.id === produto;
    });

    this.itens.push({
      id_produto: prod[0].id,
      quantidade: this.quantidade,
      total: prod[0].preco_sugerido * this.quantidade
    });
  }
}
