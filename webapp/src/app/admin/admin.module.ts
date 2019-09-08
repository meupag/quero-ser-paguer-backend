import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { AdminComponent } from './admin.component';
import { AdminRoutingModule } from './admin.routing.module';
import { ClientesComponent } from './clientes/clientes.component';
import { ProdutosComponent } from './produtos/produtos.component';
import { PedidosComponent } from './pedidos/pedidos.component';

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
  ]
})
export class AdminModule { }
