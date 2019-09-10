import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { ProdutoService } from './produto.service';

@Injectable()
export class ProdutoResolver implements Resolve<any> {

    constructor(private produtoService: ProdutoService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {

        return new Promise((resolve, reject) => {
            this.produtoService
                .getListaProdutos()
                .then((data: any) => resolve(data));
        });
    }
}