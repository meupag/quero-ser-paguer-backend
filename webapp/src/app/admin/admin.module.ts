import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { AdminComponent } from './admin.component';
import { AdminRoutingModule } from './admin.routing.module';
import { ClientesComponent } from './clientes/clientes.component';
import { ProdutosComponent } from './produtos/produtos.component';
import { PedidosComponent } from './pedidos/pedidos.component';
import { ClienteResolver } from '../services/cliente.resolve';
import { ProdutoResolver } from '../services/produto.resolve';
import { PedidoResolver } from '../services/pedido.resolve';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    ModalModule.forRoot(),
    BsDropdownModule.forRoot()
  ],
  declarations: [
    AdminComponent,
    ClientesComponent,
    ProdutosComponent,
    PedidosComponent
  ],
  providers: [ClienteResolver, ProdutoResolver, PedidoResolver]
})
export class AdminModule { }
