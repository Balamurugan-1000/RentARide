import { LoginComponent } from './pages/login/login.component';
import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { AccountActivateComponent } from './pages/account-activate/account-activate.component';
import { AuthRedirectComponent } from './shared/auth-redirect/auth-redirect.component';
import { AuthRedirectGuard } from './guards/auth-redirect.guard';
import {authGuard} from './guards/auth.guard';

export const routes: Routes = [
  {
    path: "",
    component: AuthRedirectComponent
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [AuthRedirectGuard]
  },
  {
    path: "register",
    component: RegisterComponent,

  },
  {
    path: "activate-account",
    component: AccountActivateComponent
  },
  {
    path: "vehicles",
    loadChildren: () => import('./modules/vehicle/vehicle.module').then(m => m.VehicleModule),
    canActivate: [authGuard] // Protect the vehicles route
  }
];
