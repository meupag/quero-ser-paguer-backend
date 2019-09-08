import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { ISubscription } from "rxjs/Subscription";

import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  private subscription: ISubscription;

  constructor(
    private auth: AuthService,
    private router: Router) {

    this.subscription = auth.authState.subscribe((event: string) => {

      if (event === AuthService.SIGN_IN)
        this.router.navigate(['admin'])
    });

  }

  ngOnInit() { }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
