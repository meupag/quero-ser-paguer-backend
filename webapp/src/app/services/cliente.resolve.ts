import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { ClienteService } from './cliente.service';

@Injectable()
export class ClienteResolver implements Resolve<any> {

    constructor(private cliService: ClienteService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {

        return new Promise((resolve, reject) => {

            let userInf = JSON.parse(localStorage.getItem('inf'));
            let token = userInf['accessToken'].jwtToken;

            this.cliService
                .getListaClientes(token)
                .then((data: any) => resolve(data));
        });
    }
}