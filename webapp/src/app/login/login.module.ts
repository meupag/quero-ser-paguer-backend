import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AmplifyAngularModule, AmplifyService } from 'aws-amplify-angular';

import { LoginComponent } from './login.component';
import { LoginRoutingModule } from './login.routing.module';

@NgModule({
  imports: [
    CommonModule,
    LoginRoutingModule,
    AmplifyAngularModule
  ],
  providers: [AmplifyService],
  declarations: [LoginComponent]
})
export class LoginModule { }
