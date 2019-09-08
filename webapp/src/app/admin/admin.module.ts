import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { AdminComponent } from './admin.component';
import { AdminRoutingModule } from './admin.routing.module';
import { ClientesComponent } from './clientes/clientes.component';
import { ProdutosComponent } from './produtos/produtos.component';
import { PedidosComponent } from './pedidos/pedidos.component';
import { ClienteResolver } from '../services/cliente.resolve';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    BsDropdownModule.forRoot()
  ],
  declarations: [
    AdminComponent,
    ClientesComponent,
    ProdutosComponent,
    PedidosComponent
  ],
  providers: [ClienteResolver]
})
export class AdminModule { }
