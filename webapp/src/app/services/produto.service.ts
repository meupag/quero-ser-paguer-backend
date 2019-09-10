import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  apiURL: string = 'https://9l9m6v7rg5.execute-api.us-east-1.amazonaws.com/prod';

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService,
  ) { }

  getListaProdutos() {

   return this.authService
      .currentSession()
      .then((data) => {

        let token = data['idToken'].jwtToken;

        const httpOptions = {
          headers: new HttpHeaders({
            'Authorization': token,            
            'Content-Type': 'application/json'
          })
        };
        return this.httpClient.get(`${this.apiURL}/produtos`, { headers: httpOptions.headers })
          .toPromise()
          .then((data: any) => {
            return new Promise((resolve, reject) => resolve(data));
          });
      });
  }

  cadastrarProduto(produto): any {

    return this.authService
      .currentSession()
      .then((data) => {

        let token = data['idToken'].jwtToken;

        const httpOptions = {
          headers: new HttpHeaders({
            'Authorization': token,
            'Content-Type': 'application/json'
          })
        };

        return this.httpClient.post(`${this.apiURL}/produtos`, produto, { headers: httpOptions.headers })
          .toPromise()
          .then((data: any) => {
            return new Promise((resolve, reject) => resolve(data));
          });
      });
  }
}