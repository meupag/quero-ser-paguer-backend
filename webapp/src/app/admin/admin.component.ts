import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { ISubscription } from "rxjs/Subscription";

import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {

  private subscription: ISubscription;
  collapsed: boolean;

  constructor(
    private auth: AuthService,
    private router: Router
    ) {
      this.subscription = auth.authState.subscribe((event: string) => {

        if (event === AuthService.SIGN_OUT)
          this.router.navigate(['login']);
      });
     }

  ngOnInit() { }

  toggleCollapsed(){
    this.collapsed = !this.collapsed;
  }

  clickLogout() {
    this.auth.signOut();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
