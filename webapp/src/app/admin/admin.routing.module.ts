import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AdminComponent } from './admin.component';
import { PedidosComponent } from './pedidos/pedidos.component';
import { ClientesComponent } from './clientes/clientes.component';
import { ProdutosComponent } from './produtos/produtos.component';
import { ClienteResolver } from '../services/cliente.resolve';
import { ProdutoResolver } from '../services/produto.resolve';
import { PedidoResolver } from '../services/pedido.resolve';

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
                component: ProdutosComponent,
                resolve: { produtos: ProdutoResolver }
            },
            { 
                path: 'pedidos',
                component: PedidosComponent,
                resolve: { 
                    clientes: ClienteResolver,
                    produtos: ProdutoResolver,
                    pedidos: PedidoResolver
                }
             }
        ],
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }