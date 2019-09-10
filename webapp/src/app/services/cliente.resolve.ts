import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { ClienteService } from './cliente.service';

@Injectable()
export class ClienteResolver implements Resolve<any> {

    constructor(private clienteService: ClienteService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {

        return new Promise((resolve, reject) => {
            this.clienteService
                .getListaClientes()
                .then((data: any) => resolve(data));
        });
    }
}