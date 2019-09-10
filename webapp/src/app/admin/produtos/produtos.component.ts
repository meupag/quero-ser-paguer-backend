import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

import { ProdutoService } from 'src/app/services/produto.service';

@Component({
  selector: 'app-produtos',
  templateUrl: './produtos.component.html',
  styleUrls: ['./produtos.component.css']
})
export class ProdutosComponent implements OnInit {

  @ViewChild('modal')
  modal: TemplateRef<any>;
  modalRef: BsModalRef;

  produtos: any;

  constructor(
    private route: ActivatedRoute,
    private modalService: BsModalService,
    private produtoService: ProdutoService
  ) { }

  ngOnInit() {
    this.route.data.subscribe(
      (content) => this.produtos = content.produtos
    );
  }

  addProduto() {
    this.modalRef = this.modalService.show(this.modal);
  }

  salvarProduto(nome, preco) {
    let produto = {
      id: `${this.produtos.length + 1}` ,
      nome: nome,
      preco_sugerido: `${preco}`
    }
    
    this.produtoService
      .cadastrarProduto(JSON.stringify(produto))
      .then((res) => {
        this.fecharModal();
        this.atualizarLista();
      })
  }

  fecharModal() {
    this.modalRef.hide();
  }

  atualizarLista() {
    this.produtoService
      .getListaProdutos()
      .then((data) => this.produtos = data)
  }
}
