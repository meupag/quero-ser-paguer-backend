import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { PedidoService } from './pedido.service';

@Injectable()
export class PedidoResolver implements Resolve<any> {

    constructor(private pedidoService: PedidoService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {

        return new Promise((resolve, reject) => {
            this.pedidoService
                .getListaPedidos()
                .then((data: any) => resolve(data));
        });
    }
}