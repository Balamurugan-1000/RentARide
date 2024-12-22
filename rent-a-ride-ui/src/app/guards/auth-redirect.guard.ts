import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenService } from '../services/token/token.service';

@Injectable({
  providedIn: 'root',
})
export class AuthRedirectGuard implements CanActivate {
  constructor(private router: Router, private tokenService: TokenService) {}

  canActivate(): boolean {
    const isAuthenticated = this.hasValidToken();

    if (isAuthenticated) {
      if (this.router.url !== '/vehicles') {
        this.router.navigate(['/vehicles']);
      }
      return true;
    }

    return true; // Allow route activation
  }

  private hasValidToken(): boolean {
    const token = this.tokenService.token;

    if (!token) {
      return false;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const isExpired = payload.exp && Date.now() >= payload.exp * 10000;
      return !isExpired; // Return true if not expired
    } catch (error) {
      return false; // Invalid token
    }
  }
}
