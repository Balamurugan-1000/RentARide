import { TokenService } from './services/token/token.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private tokenService: TokenService) { }

  canActivate(): boolean {
    const isAuthenticated = this.isUserAuthenticated();
    if (isAuthenticated) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }

  private isUserAuthenticated(): boolean {
    return !!this.tokenService.token;
  }
}
