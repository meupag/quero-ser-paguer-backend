import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  apiURL: string = 'https://9l9m6v7rg5.execute-api.us-east-1.amazonaws.com/prod';

  constructor(private httpClient: HttpClient) { }

  getListaClientes(token: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': token,
        'Content-Type': 'application/json'
      })
    };
    return this.httpClient.get(`${this.apiURL}/clientes`, { headers: httpOptions.headers })
      .toPromise()
      .then((data: any) => {
        return new Promise((resolve, reject) => resolve(data));
    });
  }
}