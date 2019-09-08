import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './services/auth.guard';

const routes: Routes = [
  { 
    path: '',
    redirectTo: 'admin',
    pathMatch: 'full' },
  { 
    path: 'admin',
    loadChildren: './admin/admin.module#AdminModule',
    canActivate: [ AuthGuard ]
  },
  { 
    path: 'login',
    loadChildren: './login/login.module#LoginModule'
  },
  { 
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
