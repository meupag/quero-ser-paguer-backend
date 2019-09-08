import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {

  clientes: any;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() { 
    this.route.data.subscribe(
      (content) => this.clientes = content.clientes
    );
  }
}
