import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthguardService implements CanActivate {

  constructor(
    private auth: AuthService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | boolean | any {

    console.log("true auth guard")
    return true;
 /*   return new Promise((resolve, reject) => {

      this.auth
        .isLogged()
        .subscribe(data=> {
          
          if (data) {
            resolve(true);
            return true;
          }
          else {
            
            let sigla = localStorage.getItem("sigla") || '';
            this.router.navigate([sigla]);
            resolve(false);
            return false;
          }
        });
    }); */
  }
}
