import { NgModule } from '@angular/core';
import { RouterModule, Routes, PreloadAllModules } from '@angular/router';

import { AuthGuard } from './auth.guard';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: './features/home/home.module#HomeModule'
  },
  {
    path: 'login',
    loadChildren: './features/login/login.module#LoginModule'
  },
  {
    path: 'registration',
    loadChildren: './features/registration/registration.module#RegistrationModule'
  },
  {
    path: 'editor',
    loadChildren: './features/editor/editor.module#EditorModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'profile',
    loadChildren: './features/profile/profile.module#ProfileModule',
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    loadChildren: './features/page-not-found/page-not-found.module#PageNotFoundModule',
  },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
