import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AdminComponent } from './admin.component';
import { PedidosComponent } from './pedidos/pedidos.component';
import { ClientesComponent } from './clientes/clientes.component';
import { ProdutosComponent } from './produtos/produtos.component';
import { ClienteResolver } from '../services/cliente.resolve';

const routes: Routes = [
    {
        path: '',
        component: AdminComponent,
        children: [
            { 
                path: '', 
                redirectTo: 'clientes',
                pathMatch: 'prefix'
            },
            { 
                path: 'clientes',
                component: ClientesComponent,
                resolve: { clientes: ClienteResolver }
            },
            { 
                path: 'produtos',
                component: ProdutosComponent
            },
            { 
                path: 'pedidos',
                component: PedidosComponent
             }
        ],
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }