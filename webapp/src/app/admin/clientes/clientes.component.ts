import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {

  @ViewChild('modal')
  modal: TemplateRef<any>;
  modalRef: BsModalRef;

  clientes: any;

  constructor(
    private route: ActivatedRoute,
    private modalService: BsModalService,
    private clienteService: ClienteService
  ) { }

  ngOnInit() {
    this.route.data.subscribe(
      (content) => this.clientes = content.clientes
    );
  }

  addCliente() {
    this.modalRef = this.modalService.show(this.modal);
  }

  salvarCliente(nome, cpf, data) {
    let cliente = {
      id: `${this.clientes.length + 1}` ,
      nome: nome,
      cpf: cpf,
      data: data
    }

    this.clienteService
      .cadastrarCliente(JSON.stringify(cliente))
      .then((res) => {
        this.fecharModal();
        this.atualizarLista();
      })
  }

  fecharModal() {
    this.modalRef.hide();
  }

  atualizarLista() {
    this.clienteService
      .getListaClientes()
      .then((data) => this.clientes = data)
  }
}
